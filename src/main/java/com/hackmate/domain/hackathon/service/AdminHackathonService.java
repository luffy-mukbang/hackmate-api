package com.hackmate.domain.hackathon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminHackathonService {

    private final HackathonCrawlQueueRepository crawlQueueRepository;
    private final HackathonRepository hackathonRepository;

    @Transactional(readOnly = true)
    public List<CrawlQueueItemResponse> getQueue(CrawlStatus status) {
        List<HackathonCrawlQueue> items = (status != null)
                ? crawlQueueRepository.findByCrawlStatusOrderByCrawledAtDesc(status)
                : crawlQueueRepository.findAllByOrderByCrawledAtDesc();

        return items.stream()
                .map(CrawlQueueItemResponse::from)
                .collect(Collectors.toList());
    }

    public void approve(Long queueId, ApproveQueueRequest request) {
        HackathonCrawlQueue queue = crawlQueueRepository.findById(queueId)
                .orElseThrow(CrawlQueueNotFoundException::new);

        if (queue.getCrawlStatus() != CrawlStatus.PENDING) {
            throw new CrawlQueueAlreadyProcessedException();
        }

        String name = (request != null && request.getName() != null) ? request.getName() : queue.getName();
        String organizer = (request != null && request.getOrganizer() != null) ? request.getOrganizer() : queue.getOrganizer();
        String prizeInfo = (request != null && request.getPrizeInfo() != null) ? request.getPrizeInfo() : queue.getPrizeInfo();
        String target = (request != null && request.getTarget() != null) ? request.getTarget() : queue.getTarget();
        String location = (request != null && request.getLocation() != null) ? request.getLocation() : queue.getLocation();
        HackathonStatus status = queue.getStatus() != null ? queue.getStatus() : HackathonStatus.OPEN;

        Hackathon hackathon = Hackathon.builder()
                .name(name)
                .organizer(organizer)
                .status(status)
                .thumbnailUrl(queue.getThumbnailUrl())
                .startDate(queue.getStartDate())
                .endDate(queue.getEndDate())
                .location(location)
                .prizeInfo(prizeInfo)
                .target(target)
                .homepageUrl(queue.getHomepageUrl())
                .wishCount(0)
                .build();

        hackathonRepository.save(hackathon);
        queue.approve();
    }

    public void reject(Long queueId) {
        HackathonCrawlQueue queue = crawlQueueRepository.findById(queueId)
                .orElseThrow(CrawlQueueNotFoundException::new);

        if (queue.getCrawlStatus() != CrawlStatus.PENDING) {
            throw new CrawlQueueAlreadyProcessedException();
        }

        queue.reject();
    }
}
