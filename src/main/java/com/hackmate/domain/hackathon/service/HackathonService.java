package com.hackmate.domain.hackathon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hackmate.domain.hackathon.dto.response.HackathonDetailResponse;
import com.hackmate.domain.hackathon.dto.response.HackathonListItemResponse;
import com.hackmate.domain.hackathon.entity.Hackathon;
import com.hackmate.domain.hackathon.repository.HackathonRepository;
import com.hackmate.domain.hackathon.repository.HackathonWishRepository;
import com.hackmate.domain.user.entity.User;
import com.hackmate.global.auth.SecurityUtils;
import com.hackmate.global.exception.hackathon.HackathonNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HackathonService {

    private final HackathonRepository hackathonRepository;
    private final HackathonWishRepository hackathonWishRepository;
    private final SecurityUtils securityUtils;

    public List<HackathonListItemResponse> getHackathonList() {
        Optional<User> currentUser = securityUtils.getCurrentUserOptional();
        List<Hackathon> hackathons = hackathonRepository.findAll();

        return hackathons.stream()
                .map(h -> HackathonListItemResponse.builder()
                        .id(h.getId())
                        .name(h.getName())
                        .organizer(h.getOrganizer())
                        .status(h.getStatus().name())
                        .thumbnailUrl(h.getThumbnailUrl())
                        .isWished(currentUser
                                .map(u -> hackathonWishRepository.existsByUserIdAndHackathonId(u.getId(), h.getId()))
                                .orElse(false))
                        .build())
                .collect(Collectors.toList());
    }

    public HackathonDetailResponse getHackathon(Long hackathonId) {
        Hackathon hackathon = hackathonRepository.findById(hackathonId)
                .orElseThrow(HackathonNotFoundException::new);

        Optional<User> currentUser = securityUtils.getCurrentUserOptional();
        boolean isWished = currentUser
                .map(u -> hackathonWishRepository.existsByUserIdAndHackathonId(u.getId(), hackathonId))
                .orElse(false);

        return HackathonDetailResponse.builder()
                .id(hackathon.getId())
                .name(hackathon.getName())
                .organizer(hackathon.getOrganizer())
                .status(hackathon.getStatus().name())
                .startDate(hackathon.getStartDate())
                .endDate(hackathon.getEndDate())
                .location(hackathon.getLocation())
                .prizeInfo(hackathon.getPrizeInfo())
                .target(hackathon.getTarget())
                .homepageUrl(hackathon.getHomepageUrl())
                .isWished(isWished)
                .wishCount(hackathon.getWishCount())
                .build();
    }
}
