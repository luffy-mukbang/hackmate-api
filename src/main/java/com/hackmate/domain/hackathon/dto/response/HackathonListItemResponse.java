package com.hackmate.domain.hackathon.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HackathonListItemResponse {

    private Long id;
    private String name;
    private String organizer;
    private String status;
    private String thumbnailUrl;

    @JsonProperty("isWished")
    private boolean isWished;
}
