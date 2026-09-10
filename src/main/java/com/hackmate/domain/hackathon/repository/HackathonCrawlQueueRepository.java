package com.hackmate.domain.hackathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hackmate.domain.hackathon.entity.HackathonCrawlQueue;
import com.hackmate.domain.hackathon.enums.CrawlStatus;

import java.util.List;

public interface HackathonCrawlQueueRepository extends JpaRepository<HackathonCrawlQueue, Long> {

    boolean existsBySourceUrl(String sourceUrl);

    List<HackathonCrawlQueue> findByCrawlStatusOrderByCrawledAtDesc(CrawlStatus crawlStatus);

    List<HackathonCrawlQueue> findAllByOrderByCrawledAtDesc();
}
