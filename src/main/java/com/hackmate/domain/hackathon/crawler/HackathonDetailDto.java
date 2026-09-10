package com.hackmate.domain.hackathon.crawler;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class HackathonDetailDto {

    private String thumbnailUrl;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String prizeInfo;
    private String homepageUrl;
    private String target;
    private String location;
}
