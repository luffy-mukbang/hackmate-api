package com.hackmate.domain.hackathon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import com.hackmate.domain.hackathon.dto.request.ApproveQueueRequest;
import com.hackmate.domain.hackathon.dto.response.CrawlQueueItemResponse;
import com.hackmate.domain.hackathon.enums.CrawlStatus;
import com.hackmate.domain.hackathon.service.AdminHackathonService;
import com.hackmate.global.auth.TokenProvider;
import com.hackmate.global.auth.CustomUserDetailsService;
import com.hackmate.global.config.SecurityConfig;
import com.hackmate.global.exception.hackathon.CrawlQueueAlreadyProcessedException;
import com.hackmate.global.exception.hackathon.CrawlQueueNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminHackathonController.class)
@Import(SecurityConfig.class)
class AdminHackathonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AdminHackathonService adminHackathonService;

    @MockitoBean
    private TokenProvider tokenProvider;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    private CrawlQueueItemResponse buildQueueItem(long id) {
        return CrawlQueueItemResponse.builder()
                .id(id).name("해커톤" + id).organizer("주최기관")
                .status("OPEN").crawlStatus("PENDING")
                .sourceSite("WEVITY").sourceUrl("https://wevity.com/detail?ix=" + id)
                .crawledAt(LocalDateTime.now())
                .build();
    }

    // ─────────────────────────────────────────────
    // GET /admin/hackathons/queue
    // ─────────────────────────────────────────────

    @Test
    @DisplayName("큐 목록 조회 - ADMIN 권한으로 200 OK")
    @WithMockUser(roles = "ADMIN")
    void getQueue_admin_200() throws Exception {
        given(adminHackathonService.getQueue(null))
                .willReturn(List.of(buildQueueItem(1L), buildQueueItem(2L)));

        mockMvc.perform(get("/admin/hackathons/queue"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].crawlStatus").value("PENDING"));
    }

    @Test
    @DisplayName("큐 목록 조회 - status 파라미터로 필터")
    @WithMockUser(roles = "ADMIN")
    void getQueue_withStatusFilter() throws Exception {
        given(adminHackathonService.getQueue(CrawlStatus.PENDING))
                .willReturn(List.of(buildQueueItem(1L)));

        mockMvc.perform(get("/admin/hackathons/queue").param("status", "PENDING"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(1));
    }

    @Test
    @DisplayName("큐 목록 조회 - USER 권한이면 403")
    @WithMockUser(roles = "USER")
    void getQueue_user_403() throws Exception {
        mockMvc.perform(get("/admin/hackathons/queue"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("큐 목록 조회 - 인증 없으면 403")
    void getQueue_noAuth_403() throws Exception {
        mockMvc.perform(get("/admin/hackathons/queue"))
                .andExpect(status().isForbidden());
    }

    // ─────────────────────────────────────────────
    // POST /admin/hackathons/queue/{queueId}/approve
    // ─────────────────────────────────────────────

    @Test
    @DisplayName("승인 - ADMIN 권한으로 200 OK")
    @WithMockUser(roles = "ADMIN")
    void approve_admin_200() throws Exception {
        mockMvc.perform(post("/admin/hackathons/queue/1/approve")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @DisplayName("승인 - location 포함 요청 200 OK")
    @WithMockUser(roles = "ADMIN")
    void approve_withBody_200() throws Exception {
        ApproveQueueRequest req = ApproveQueueRequest.builder()
                .location("서울특별시 강남구")
                .build();

        mockMvc.perform(post("/admin/hackathons/queue/1/approve")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @DisplayName("승인 - 존재하지 않는 항목이면 404")
    @WithMockUser(roles = "ADMIN")
    void approve_notFound_404() throws Exception {
        doThrow(new CrawlQueueNotFoundException())
                .when(adminHackathonService).approve(eq(999L), any());

        mockMvc.perform(post("/admin/hackathons/queue/999/approve")
                        .with(csrf()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.code").value("CRAWL_QUEUE_NOT_FOUND"));
    }

    @Test
    @DisplayName("승인 - 이미 처리된 항목이면 409")
    @WithMockUser(roles = "ADMIN")
    void approve_alreadyProcessed_409() throws Exception {
        doThrow(new CrawlQueueAlreadyProcessedException())
                .when(adminHackathonService).approve(eq(1L), any());

        mockMvc.perform(post("/admin/hackathons/queue/1/approve")
                        .with(csrf()))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("CRAWL_QUEUE_ALREADY_PROCESSED"));
    }

    @Test
    @DisplayName("승인 - USER 권한이면 403")
    @WithMockUser(roles = "USER")
    void approve_user_403() throws Exception {
        mockMvc.perform(post("/admin/hackathons/queue/1/approve")
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    // ─────────────────────────────────────────────
    // POST /admin/hackathons/queue/{queueId}/reject
    // ─────────────────────────────────────────────

    @Test
    @DisplayName("거절 - ADMIN 권한으로 200 OK")
    @WithMockUser(roles = "ADMIN")
    void reject_admin_200() throws Exception {
        mockMvc.perform(post("/admin/hackathons/queue/1/reject")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @DisplayName("거절 - 존재하지 않는 항목이면 404")
    @WithMockUser(roles = "ADMIN")
    void reject_notFound_404() throws Exception {
        doThrow(new CrawlQueueNotFoundException())
                .when(adminHackathonService).reject(999L);

        mockMvc.perform(post("/admin/hackathons/queue/999/reject")
                        .with(csrf()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("CRAWL_QUEUE_NOT_FOUND"));
    }

    @Test
    @DisplayName("거절 - 이미 처리된 항목이면 409")
    @WithMockUser(roles = "ADMIN")
    void reject_alreadyProcessed_409() throws Exception {
        doThrow(new CrawlQueueAlreadyProcessedException())
                .when(adminHackathonService).reject(1L);

        mockMvc.perform(post("/admin/hackathons/queue/1/reject")
                        .with(csrf()))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("CRAWL_QUEUE_ALREADY_PROCESSED"));
    }

    @Test
    @DisplayName("거절 - USER 권한이면 403")
    @WithMockUser(roles = "USER")
    void reject_user_403() throws Exception {
        mockMvc.perform(post("/admin/hackathons/queue/1/reject")
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }
}
