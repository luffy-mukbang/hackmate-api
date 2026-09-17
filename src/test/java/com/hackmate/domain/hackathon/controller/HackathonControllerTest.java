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
import org.springframework.test.web.servlet.MockMvc;
import com.hackmate.domain.hackathon.dto.response.HackathonDetailResponse;
import com.hackmate.domain.hackathon.dto.response.HackathonListItemResponse;
import com.hackmate.domain.hackathon.service.HackathonService;
import com.hackmate.global.auth.TokenProvider;
import com.hackmate.global.auth.CustomUserDetailsService;
import com.hackmate.global.config.SecurityConfig;
import com.hackmate.global.exception.hackathon.HackathonNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HackathonController.class)
@Import(SecurityConfig.class)
class HackathonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private HackathonService hackathonService;

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

    @Test
    @DisplayName("해커톤 목록 조회 - 200 OK")
    void getHackathonList_success() throws Exception {
        List<HackathonListItemResponse> mockList = List.of(
                HackathonListItemResponse.builder()
                        .id(1L).name("해커톤A").organizer("기관A")
                        .status("OPEN").thumbnailUrl("https://thumb1.jpg").isWished(false)
                        .build(),
                HackathonListItemResponse.builder()
                        .id(2L).name("해커톤B").organizer("기관B")
                        .status("UPCOMING").thumbnailUrl("https://thumb2.jpg").isWished(true)
                        .build()
        );
        given(hackathonService.getHackathonList()).willReturn(mockList);

        mockMvc.perform(get("/hackathons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].name").value("해커톤A"))
                .andExpect(jsonPath("$.data[0].status").value("OPEN"))
                .andExpect(jsonPath("$.data[0].isWished").value(false))
                .andExpect(jsonPath("$.data[1].isWished").value(true));
    }

    @Test
    @DisplayName("해커톤 목록 조회 - 빈 목록 200 OK")
    void getHackathonList_empty() throws Exception {
        given(hackathonService.getHackathonList()).willReturn(List.of());

        mockMvc.perform(get("/hackathons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(0));
    }

    @Test
    @DisplayName("해커톤 상세 조회 - 200 OK")
    void getHackathon_success() throws Exception {
        HackathonDetailResponse mockDetail = HackathonDetailResponse.builder()
                .id(1L).name("해커톤A").organizer("기관A")
                .status("OPEN")
                .startDate(LocalDateTime.of(2026, 7, 1, 0, 0))
                .endDate(LocalDateTime.of(2026, 7, 31, 0, 0))
                .location(null)
                .prizeInfo("1천만원").target("대학생")
                .homepageUrl("https://example.com")
                .isWished(false).wishCount(10)
                .build();
        given(hackathonService.getHackathon(1L)).willReturn(mockDetail);

        mockMvc.perform(get("/hackathons/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("해커톤A"))
                .andExpect(jsonPath("$.data.organizer").value("기관A"))
                .andExpect(jsonPath("$.data.status").value("OPEN"))
                .andExpect(jsonPath("$.data.prizeInfo").value("1천만원"))
                .andExpect(jsonPath("$.data.target").value("대학생"))
                .andExpect(jsonPath("$.data.homepageUrl").value("https://example.com"))
                .andExpect(jsonPath("$.data.wishCount").value(10))
                .andExpect(jsonPath("$.data.isWished").value(false));
    }

    @Test
    @DisplayName("해커톤 상세 조회 - 존재하지 않는 ID 시 404")
    void getHackathon_notFound_returns404() throws Exception {
        given(hackathonService.getHackathon(999L)).willThrow(new HackathonNotFoundException());

        mockMvc.perform(get("/hackathons/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.code").value("HACKATHON_NOT_FOUND"));
    }

    @Test
    @DisplayName("해커톤 목록 조회 - 인증 없이도 접근 가능")
    void getHackathonList_noAuth_200() throws Exception {
        given(hackathonService.getHackathonList()).willReturn(List.of());

        mockMvc.perform(get("/hackathons"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("해커톤 상세 조회 - 인증 없이도 접근 가능")
    void getHackathon_noAuth_200() throws Exception {
        HackathonDetailResponse mockDetail = HackathonDetailResponse.builder()
                .id(1L).name("해커톤A").organizer("기관A")
                .status("OPEN").isWished(false).wishCount(0)
                .startDate(LocalDateTime.now()).endDate(LocalDateTime.now().plusDays(30))
                .build();
        given(hackathonService.getHackathon(1L)).willReturn(mockDetail);

        mockMvc.perform(get("/hackathons/1"))
                .andExpect(status().isOk());
    }
}
