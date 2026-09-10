package com.hackmate.domain.hackathon.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApproveQueueRequest {

    private String location;
    private String name;
    private String organizer;
    private String prizeInfo;
    private String target;
}
