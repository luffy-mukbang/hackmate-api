package com.hackmate.domain.hackathon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.hackmate.domain.hackathon.dto.response.HackathonDetailResponse;
import com.hackmate.domain.hackathon.dto.response.HackathonListItemResponse;
import com.hackmate.domain.hackathon.service.HackathonService;
import com.hackmate.global.response.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/hackathons")
@RequiredArgsConstructor
public class HackathonController {

    private final HackathonService hackathonService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<HackathonListItemResponse>>> getHackathonList() {
        return ResponseEntity.ok(ApiResponse.ok(hackathonService.getHackathonList()));
    }

    @GetMapping("/{hackathonId}")
    public ResponseEntity<ApiResponse<HackathonDetailResponse>> getHackathon(
            @PathVariable Long hackathonId) {
        return ResponseEntity.ok(ApiResponse.ok(hackathonService.getHackathon(hackathonId)));
    }
}
