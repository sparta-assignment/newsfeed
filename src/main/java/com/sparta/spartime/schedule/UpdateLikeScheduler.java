package com.sparta.spartime.schedule;

import com.sparta.spartime.repository.LikeRepository;
import com.sparta.spartime.service.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j(topic = "update like scheduler")
@RequiredArgsConstructor
@Component
public class UpdateLikeScheduler {

    private final LikeService likeService;

    /**
     * like수를 맞춰주기 위해 스케줄러 실행
     */
    @Scheduled(cron = "0 10 0 * * *")
    public void updateLike() {
        log.info("update like start");
        likeService.updateLike();
    }
}
