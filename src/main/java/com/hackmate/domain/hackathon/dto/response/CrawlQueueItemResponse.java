package com.hackmate.domain.hackathon.dto.response;

import lombok.Builder;
import lombok.Getter;
import com.hackmate.domain.hackathon.entity.HackathonCrawlQueue;

import java.time.LocalDateTime;

@Getter
@Builder
public class CrawlQueueItemResponse {

    private Long id;
    private String name;
    private String organizer;
    private String status;
    private String thumbnailUrl;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String location;
    private String prizeInfo;
    private String target;
    private String homepageUrl;
    private String sourceUrl;
    private String sourceSite;
    private String crawlStatus;
    private LocalDateTime crawledAt;

    public static CrawlQueueItemResponse from(HackathonCrawlQueue queue) {
        return CrawlQueueItemResponse.builder()
                .id(queue.getId())
                .name(queue.getName())
                .organizer(queue.getOrganizer())
                .status(queue.getStatus() != null ? queue.getStatus().name() : null)
                .thumbnailUrl(queue.getThumbnailUrl())
                .startDate(queue.getStartDate())
                .endDate(queue.getEndDate())
                .location(queue.getLocation())
                .prizeInfo(queue.getPrizeInfo())
                .target(queue.getTarget())
                .homepageUrl(queue.getHomepageUrl())
                .sourceUrl(queue.getSourceUrl())
                .sourceSite(queue.getSourceSite())
                .crawlStatus(queue.getCrawlStatus().name())
                .crawledAt(queue.getCrawledAt())
                .build();
    }
}
