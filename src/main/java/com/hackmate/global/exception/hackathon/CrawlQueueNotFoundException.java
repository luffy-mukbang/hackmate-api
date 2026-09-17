package com.hackmate.global.exception.hackathon;

import org.springframework.http.HttpStatus;
import com.hackmate.global.exception.DomainException;

public class CrawlQueueNotFoundException extends DomainException {
    public CrawlQueueNotFoundException() {
        super(HttpStatus.NOT_FOUND, "CRAWL_QUEUE_NOT_FOUND", "존재하지 않는 크롤링 항목입니다.");
    }
}
