package com.hackmate.domain.hackathon.crawler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import com.hackmate.domain.hackathon.crawler.HackathonDetailDto;
import com.hackmate.domain.hackathon.entity.HackathonCrawlQueue;
import com.hackmate.domain.hackathon.enums.CrawlStatus;
import com.hackmate.domain.hackathon.enums.HackathonStatus;
import com.hackmate.domain.hackathon.repository.HackathonCrawlQueueRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class WevityCrawlerService {

    private final HackathonCrawlQueueRepository crawlQueueRepository;

    private static final String BASE_URL = "https://www.wevity.com";
    // 해커톤 관련 카테고리: 웹/모바일/IT(20), 게임/소프트웨어(21), 과학/공학(22)
    private static final int[] HACKATHON_CATEGORY_IDS = {20, 21, 22};

    public void crawlAll() {
        for (int cidx : HACKATHON_CATEGORY_IDS) {
            try {
                crawlCategory(cidx);
                Thread.sleep(2000);
            } catch (Exception e) {
                log.error("카테고리 {} 크롤링 실패: {}", cidx, e.getMessage());
            }
        }
    }

    private void crawlCategory(int cidx) throws IOException, InterruptedException {
        int page = 1;

        while (true) {
            String url = BASE_URL + "/?c=find&s=1&gub=1&cidx=" + cidx + "&gp=" + page;
            log.info("크롤링 중: {}", url);

            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                    .timeout(10_000)
                    .get();

            Elements items = doc.select("ul.list li:not(.top)");
            if (items.isEmpty()) break;

            for (Element item : items) {
                try {
                    parseAndSave(item);
                    Thread.sleep(1000);
                } catch (Exception e) {
                    log.warn("항목 파싱 실패: {}", e.getMessage());
                }
            }

            Element nextPage = doc.selectFirst("div.list-navi a[href*='gp=" + (page + 1) + "']");
            if (nextPage == null) break;

            page++;
            Thread.sleep(2000);
        }
    }

    private void parseAndSave(Element item) throws IOException, InterruptedException {
        Element titleLink = item.selectFirst("div.tit a");
        if (titleLink == null) return;

        String name = titleLink.text()
                .replaceAll("신규|SPECIAL|IDEA|NEW", "").trim();
        String detailPath = titleLink.attr("href");
        String detailUrl = BASE_URL + "/" + detailPath;

        String organizer = "";
        Element organEl = item.selectFirst("div.organ");
        if (organEl != null) organizer = organEl.text().trim();

        HackathonStatus status = HackathonStatus.OPEN;
        Element dayEl = item.selectFirst("div.day");
        if (dayEl != null) {
            Element statusEl = dayEl.selectFirst("span.dday");
            if (statusEl != null) {
                status = switch (statusEl.text().trim()) {
                    case "접수중" -> HackathonStatus.OPEN;
                    case "접수예정" -> HackathonStatus.UPCOMING;
                    case "마감임박" -> HackathonStatus.OPEN;
                    case "마감" -> HackathonStatus.CLOSED;
                    default -> HackathonStatus.OPEN;
                };
            }
        }

        if (crawlQueueRepository.existsBySourceUrl(detailUrl)) {
            log.info("중복 스킵: {}", name);
            return;
        }

        Thread.sleep(1500);
        HackathonDetailDto detail = crawlDetail(detailUrl);

        HackathonCrawlQueue queue = HackathonCrawlQueue.builder()
                .name(name)
                .organizer(organizer)
                .status(status)
                .homepageUrl(detail.getHomepageUrl())
                .thumbnailUrl(detail.getThumbnailUrl())
                .startDate(detail.getStartDate())
                .endDate(detail.getEndDate())
                .prizeInfo(detail.getPrizeInfo())
                .target(detail.getTarget())
                .location(null)
                .sourceUrl(detailUrl)
                .sourceSite("WEVITY")
                .crawlStatus(CrawlStatus.PENDING)
                .crawledAt(LocalDateTime.now())
                .build();

        crawlQueueRepository.save(queue);
        log.info("저장 완료: {}", name);
    }

    private HackathonDetailDto crawlDetail(String detailUrl) {
        try {
            Document doc = Jsoup.connect(detailUrl)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                    .timeout(10_000)
                    .get();

            // hidden input에서 파싱
            String during = getHiddenValue(doc, "during");
            String prizeInfo = getHiddenValue(doc, "money1");
            String homepageUrl = getHiddenValue(doc, "homepage");

            // 날짜 파싱: "2026-06-29 ~ 2026-07-24 D-26"
            LocalDateTime startDate = null;
            LocalDateTime endDate = null;
            if (during != null && during.contains("~")) {
                String[] parts = during.split("~");
                startDate = parseDate(parts[0].trim());
                endDate = parseDate(parts[1].trim().split(" ")[0]);
            }

            // 썸네일
            String thumbnailUrl = "";
            Element thumb = doc.selectFirst("div.thumb img");
            if (thumb != null) {
                String src = thumb.attr("src");
                thumbnailUrl = src.startsWith("http") ? src : BASE_URL + src;
            }

            // 응모대상
            String target = "";
            for (Element li : doc.select("ul.cd-info-list li")) {
                Element titEl = li.selectFirst("span.tit");
                if (titEl != null && titEl.text().equals("응모대상")) {
                    target = li.ownText().trim();
                    break;
                }
            }

            return HackathonDetailDto.builder()
                    .thumbnailUrl(thumbnailUrl)
                    .startDate(startDate)
                    .endDate(endDate)
                    .prizeInfo(prizeInfo)
                    .homepageUrl(homepageUrl != null ? homepageUrl : detailUrl)
                    .target(target)
                    .location(null)
                    .build();

        } catch (IOException e) {
            log.warn("상세 페이지 파싱 실패: {}", e.getMessage());
            return HackathonDetailDto.builder()
                    .homepageUrl(detailUrl)
                    .build();
        }
    }

    private String getHiddenValue(Document doc, String name) {
        Element el = doc.selectFirst("form#efrm input[name=" + name + "]");
        return el != null ? el.attr("value") : null;
    }

    private LocalDateTime parseDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr.trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    .atStartOfDay();
        } catch (Exception e) {
            log.warn("날짜 파싱 실패: {}", dateStr);
            return null;
        }
    }
}
