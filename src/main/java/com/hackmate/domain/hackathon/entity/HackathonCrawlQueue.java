package com.hackmate.domain.hackathon.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.hackmate.domain.hackathon.enums.CrawlStatus;
import com.hackmate.domain.hackathon.enums.HackathonStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "hackathon_crawl_queue")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class HackathonCrawlQueue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "organizer")
    private String organizer;

    @Column(name = "hackathon_status")
    @Enumerated(EnumType.STRING)
    private HackathonStatus status;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "location")
    private String location;

    @Column(name = "prize_info")
    private String prizeInfo;

    @Column(name = "target")
    private String target;

    @Column(name = "homepage_url")
    private String homepageUrl;

    @Column(name = "source_url", unique = true)
    private String sourceUrl;

    @Column(name = "source_site")
    private String sourceSite;

    @Column(name = "crawl_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CrawlStatus crawlStatus;

    @Column(name = "crawled_at")
    private LocalDateTime crawledAt;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public void approve() {
        this.crawlStatus = CrawlStatus.APPROVED;
    }

    public void reject() {
        this.crawlStatus = CrawlStatus.REJECTED;
    }
}
