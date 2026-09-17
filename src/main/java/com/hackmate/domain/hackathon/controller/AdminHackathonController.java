package com.hackmate.domain.hackathon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.hackmate.domain.hackathon.dto.request.ApproveQueueRequest;
import com.hackmate.domain.hackathon.dto.response.CrawlQueueItemResponse;
import com.hackmate.domain.hackathon.enums.CrawlStatus;
import com.hackmate.domain.hackathon.service.AdminHackathonService;
import com.hackmate.global.response.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/admin/hackathons")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminHackathonController {

    private final AdminHackathonService adminHackathonService;

    @GetMapping("/queue")
    public ResponseEntity<ApiResponse<List<CrawlQueueItemResponse>>> getQueue(
            @RequestParam(required = false) CrawlStatus status) {
        return ResponseEntity.ok(ApiResponse.ok(adminHackathonService.getQueue(status)));
    }

    @PostMapping("/queue/{queueId}/approve")
    public ResponseEntity<ApiResponse<Void>> approve(
            @PathVariable Long queueId,
            @RequestBody(required = false) ApproveQueueRequest request) {
        adminHackathonService.approve(queueId, request);
        return ResponseEntity.ok(ApiResponse.ok("해커톤이 승인되어 등록되었습니다."));
    }

    @PostMapping("/queue/{queueId}/reject")
    public ResponseEntity<ApiResponse<Void>> reject(@PathVariable Long queueId) {
        adminHackathonService.reject(queueId);
        return ResponseEntity.ok(ApiResponse.ok("해커톤 크롤링 항목이 거절되었습니다."));
    }
}
