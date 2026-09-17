package com.hackmate.domain.hackathon.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.hackmate.domain.hackathon.dto.request.ApproveQueueRequest;
import com.hackmate.domain.hackathon.dto.response.CrawlQueueItemResponse;
import com.hackmate.domain.hackathon.entity.Hackathon;
import com.hackmate.domain.hackathon.entity.HackathonCrawlQueue;
import com.hackmate.domain.hackathon.enums.CrawlStatus;
import com.hackmate.domain.hackathon.enums.HackathonStatus;
import com.hackmate.domain.hackathon.repository.HackathonCrawlQueueRepository;
import com.hackmate.domain.hackathon.repository.HackathonRepository;
import com.hackmate.global.exception.hackathon.CrawlQueueAlreadyProcessedException;
import com.hackmate.global.exception.hackathon.CrawlQueueNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AdminHackathonServiceTest {

    @InjectMocks
    private AdminHackathonService adminHackathonService;

    @Mock
    private HackathonCrawlQueueRepository crawlQueueRepository;

    @Mock
    private HackathonRepository hackathonRepository;

    private HackathonCrawlQueue buildPendingQueue(long id) {
        return HackathonCrawlQueue.builder()
                .id(id)
                .name("해커톤" + id)
                .organizer("주최기관")
                .status(HackathonStatus.OPEN)
                .thumbnailUrl("https://thumb.jpg")
                .startDate(LocalDateTime.of(2026, 7, 1, 0, 0))
                .endDate(LocalDateTime.of(2026, 7, 31, 0, 0))
                .prizeInfo("1천만원")
                .target("대학생")
                .homepageUrl("https://example.com")
                .sourceUrl("https://wevity.com/detail?ix=" + id)
                .sourceSite("WEVITY")
                .crawlStatus(CrawlStatus.PENDING)
                .crawledAt(LocalDateTime.now())
                .build();
    }

    // ─────────────────────────────────────────────
    // getQueue
    // ─────────────────────────────────────────────

    @Test
    @DisplayName("큐 목록 조회 - status 파라미터 없으면 전체 반환")
    void getQueue_noFilter_returnsAll() {
        given(crawlQueueRepository.findAllByOrderByCrawledAtDesc())
                .willReturn(List.of(buildPendingQueue(1L), buildPendingQueue(2L)));

        List<CrawlQueueItemResponse> result = adminHackathonService.getQueue(null);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getCrawlStatus()).isEqualTo("PENDING");
    }

    @Test
    @DisplayName("큐 목록 조회 - PENDING 필터")
    void getQueue_pendingFilter() {
        given(crawlQueueRepository.findByCrawlStatusOrderByCrawledAtDesc(CrawlStatus.PENDING))
                .willReturn(List.of(buildPendingQueue(1L)));

        List<CrawlQueueItemResponse> result = adminHackathonService.getQueue(CrawlStatus.PENDING);

        assertThat(result).hasSize(1);
    }

    // ─────────────────────────────────────────────
    // approve
    // ─────────────────────────────────────────────

    @Test
    @DisplayName("승인 - 크롤링 항목을 hackathons 테이블에 저장")
    void approve_savesHackathon() {
        HackathonCrawlQueue queue = buildPendingQueue(1L);
        given(crawlQueueRepository.findById(1L)).willReturn(Optional.of(queue));

        adminHackathonService.approve(1L, null);

        ArgumentCaptor<Hackathon> captor = ArgumentCaptor.forClass(Hackathon.class);
        verify(hackathonRepository).save(captor.capture());
        Hackathon saved = captor.getValue();

        assertThat(saved.getName()).isEqualTo("해커톤1");
        assertThat(saved.getOrganizer()).isEqualTo("주최기관");
        assertThat(saved.getStatus()).isEqualTo(HackathonStatus.OPEN);
        assertThat(saved.getWishCount()).isZero();
    }

    @Test
    @DisplayName("승인 - request로 location 덮어쓰기 적용")
    void approve_withLocationOverride() {
        HackathonCrawlQueue queue = buildPendingQueue(1L);
        given(crawlQueueRepository.findById(1L)).willReturn(Optional.of(queue));

        ApproveQueueRequest req = ApproveQueueRequest.builder()
                .location("서울특별시 강남구")
                .build();

        adminHackathonService.approve(1L, req);

        ArgumentCaptor<Hackathon> captor = ArgumentCaptor.forClass(Hackathon.class);
        verify(hackathonRepository).save(captor.capture());
        assertThat(captor.getValue().getLocation()).isEqualTo("서울특별시 강남구");
    }

    @Test
    @DisplayName("승인 - 큐 상태가 APPROVED로 변경")
    void approve_updatesQueueStatus() {
        HackathonCrawlQueue queue = buildPendingQueue(1L);
        given(crawlQueueRepository.findById(1L)).willReturn(Optional.of(queue));

        adminHackathonService.approve(1L, null);

        assertThat(queue.getCrawlStatus()).isEqualTo(CrawlStatus.APPROVED);
    }

    @Test
    @DisplayName("승인 - 존재하지 않는 항목이면 CrawlQueueNotFoundException")
    void approve_notFound() {
        given(crawlQueueRepository.findById(999L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> adminHackathonService.approve(999L, null))
                .isInstanceOf(CrawlQueueNotFoundException.class);

        verify(hackathonRepository, never()).save(any());
    }

    @Test
    @DisplayName("승인 - 이미 처리된 항목이면 CrawlQueueAlreadyProcessedException")
    void approve_alreadyProcessed() {
        HackathonCrawlQueue queue = buildPendingQueue(1L);
        queue.approve();
        given(crawlQueueRepository.findById(1L)).willReturn(Optional.of(queue));

        assertThatThrownBy(() -> adminHackathonService.approve(1L, null))
                .isInstanceOf(CrawlQueueAlreadyProcessedException.class);

        verify(hackathonRepository, never()).save(any());
    }

    // ─────────────────────────────────────────────
    // reject
    // ─────────────────────────────────────────────

    @Test
    @DisplayName("거절 - 큐 상태가 REJECTED로 변경")
    void reject_updatesQueueStatus() {
        HackathonCrawlQueue queue = buildPendingQueue(1L);
        given(crawlQueueRepository.findById(1L)).willReturn(Optional.of(queue));

        adminHackathonService.reject(1L);

        assertThat(queue.getCrawlStatus()).isEqualTo(CrawlStatus.REJECTED);
    }

    @Test
    @DisplayName("거절 - 존재하지 않는 항목이면 CrawlQueueNotFoundException")
    void reject_notFound() {
        given(crawlQueueRepository.findById(999L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> adminHackathonService.reject(999L))
                .isInstanceOf(CrawlQueueNotFoundException.class);
    }

    @Test
    @DisplayName("거절 - 이미 처리된 항목이면 CrawlQueueAlreadyProcessedException")
    void reject_alreadyProcessed() {
        HackathonCrawlQueue queue = buildPendingQueue(1L);
        queue.reject();
        given(crawlQueueRepository.findById(1L)).willReturn(Optional.of(queue));

        assertThatThrownBy(() -> adminHackathonService.reject(1L))
                .isInstanceOf(CrawlQueueAlreadyProcessedException.class);
    }
}
