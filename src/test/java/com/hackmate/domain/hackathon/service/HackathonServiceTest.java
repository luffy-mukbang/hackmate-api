package com.hackmate.domain.hackathon.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.hackmate.domain.hackathon.dto.response.HackathonDetailResponse;
import com.hackmate.domain.hackathon.dto.response.HackathonListItemResponse;
import com.hackmate.domain.hackathon.entity.Hackathon;
import com.hackmate.domain.hackathon.enums.HackathonStatus;
import com.hackmate.domain.hackathon.repository.HackathonRepository;
import com.hackmate.domain.hackathon.repository.HackathonWishRepository;
import com.hackmate.domain.user.entity.User;
import com.hackmate.global.auth.SecurityUtils;
import com.hackmate.global.exception.hackathon.HackathonNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class HackathonServiceTest {

    @InjectMocks
    private HackathonService hackathonService;

    @Mock
    private HackathonRepository hackathonRepository;

    @Mock
    private HackathonWishRepository hackathonWishRepository;

    @Mock
    private SecurityUtils securityUtils;

    private Hackathon buildHackathon(long id) {
        return Hackathon.builder()
                .id(id)
                .name("해커톤" + id)
                .organizer("주최기관")
                .status(HackathonStatus.OPEN)
                .thumbnailUrl("https://example.com/thumb.jpg")
                .wishCount(5)
                .startDate(LocalDateTime.of(2026, 7, 1, 0, 0))
                .endDate(LocalDateTime.of(2026, 7, 31, 0, 0))
                .prizeInfo("1천만원")
                .target("대학생")
                .homepageUrl("https://example.com")
                .build();
    }

    @Test
    @DisplayName("목록 조회 - 비로그인 시 isWished false 반환")
    void getHackathonList_anonymous_isWishedFalse() {
        given(securityUtils.getCurrentUserOptional()).willReturn(Optional.empty());
        given(hackathonRepository.findAll()).willReturn(List.of(buildHackathon(1L), buildHackathon(2L)));

        List<HackathonListItemResponse> result = hackathonService.getHackathonList();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).isWished()).isFalse();
        assertThat(result.get(1).isWished()).isFalse();
    }

    @Test
    @DisplayName("목록 조회 - 로그인 유저, 위시리스트 반영")
    void getHackathonList_authenticated_isWishedReflected() {
        User user = mock(User.class);
        given(user.getId()).willReturn(10L);
        given(securityUtils.getCurrentUserOptional()).willReturn(Optional.of(user));
        given(hackathonRepository.findAll()).willReturn(List.of(buildHackathon(1L), buildHackathon(2L)));
        given(hackathonWishRepository.existsByUserIdAndHackathonId(10L, 1L)).willReturn(true);
        given(hackathonWishRepository.existsByUserIdAndHackathonId(10L, 2L)).willReturn(false);

        List<HackathonListItemResponse> result = hackathonService.getHackathonList();

        assertThat(result.get(0).isWished()).isTrue();
        assertThat(result.get(1).isWished()).isFalse();
    }

    @Test
    @DisplayName("목록 조회 - 빈 목록이면 빈 리스트 반환")
    void getHackathonList_empty() {
        given(securityUtils.getCurrentUserOptional()).willReturn(Optional.empty());
        given(hackathonRepository.findAll()).willReturn(List.of());

        List<HackathonListItemResponse> result = hackathonService.getHackathonList();

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("상세 조회 - 존재하는 해커톤, 비로그인")
    void getHackathon_found_anonymous() {
        given(securityUtils.getCurrentUserOptional()).willReturn(Optional.empty());
        given(hackathonRepository.findById(1L)).willReturn(Optional.of(buildHackathon(1L)));

        HackathonDetailResponse result = hackathonService.getHackathon(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("해커톤1");
        assertThat(result.getStatus()).isEqualTo("OPEN");
        assertThat(result.getWishCount()).isEqualTo(5);
        assertThat(result.isWished()).isFalse();
    }

    @Test
    @DisplayName("상세 조회 - 로그인 유저, isWished true")
    void getHackathon_authenticated_isWishedTrue() {
        User user = mock(User.class);
        given(user.getId()).willReturn(10L);
        given(securityUtils.getCurrentUserOptional()).willReturn(Optional.of(user));
        given(hackathonRepository.findById(1L)).willReturn(Optional.of(buildHackathon(1L)));
        given(hackathonWishRepository.existsByUserIdAndHackathonId(10L, 1L)).willReturn(true);

        HackathonDetailResponse result = hackathonService.getHackathon(1L);

        assertThat(result.isWished()).isTrue();
    }

    @Test
    @DisplayName("상세 조회 - 존재하지 않는 해커톤이면 HackathonNotFoundException")
    void getHackathon_notFound_throwsException() {
        given(hackathonRepository.findById(999L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> hackathonService.getHackathon(999L))
                .isInstanceOf(HackathonNotFoundException.class);
    }

    @Test
    @DisplayName("상세 조회 - 응답 필드 검증")
    void getHackathon_responseFields() {
        given(securityUtils.getCurrentUserOptional()).willReturn(Optional.empty());
        given(hackathonRepository.findById(1L)).willReturn(Optional.of(buildHackathon(1L)));

        HackathonDetailResponse result = hackathonService.getHackathon(1L);

        assertThat(result.getOrganizer()).isEqualTo("주최기관");
        assertThat(result.getPrizeInfo()).isEqualTo("1천만원");
        assertThat(result.getTarget()).isEqualTo("대학생");
        assertThat(result.getHomepageUrl()).isEqualTo("https://example.com");
        assertThat(result.getStartDate()).isEqualTo(LocalDateTime.of(2026, 7, 1, 0, 0));
        assertThat(result.getEndDate()).isEqualTo(LocalDateTime.of(2026, 7, 31, 0, 0));
    }
}
