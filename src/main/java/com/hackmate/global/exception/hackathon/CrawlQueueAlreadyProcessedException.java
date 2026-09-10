package com.hackmate.global.exception.hackathon;

import org.springframework.http.HttpStatus;
import com.hackmate.global.exception.DomainException;

public class CrawlQueueAlreadyProcessedException extends DomainException {
    public CrawlQueueAlreadyProcessedException() {
        super(HttpStatus.CONFLICT, "CRAWL_QUEUE_ALREADY_PROCESSED", "이미 처리된 크롤링 항목입니다.");
    }
}
