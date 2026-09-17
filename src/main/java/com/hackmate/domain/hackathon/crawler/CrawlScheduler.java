package com.hackmate.domain.hackathon.crawler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CrawlScheduler {

    private final WevityCrawlerService crawlerService;

    @Scheduled(cron = "0 0 3 * * *")
    public void crawl() {
        log.info("위비티 크롤링 시작");
        crawlerService.crawlAll();
        log.info("위비티 크롤링 완료");
    }
}
