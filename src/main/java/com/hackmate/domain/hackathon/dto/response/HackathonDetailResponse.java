package com.hackmate.domain.hackathon.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class HackathonDetailResponse {

    private Long id;
    private String name;
    private String organizer;
    private String status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String location;
    private String prizeInfo;
    private String target;
    private String homepageUrl;

    @JsonProperty("isWished")
    private boolean isWished;

    private int wishCount;
}
