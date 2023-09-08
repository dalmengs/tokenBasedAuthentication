package com.baseproject.tokenbasedauthentication.Scheduler;

import com.baseproject.tokenbasedauthentication.Repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class RefreshTokenScheduler {

    private final RefreshTokenRepository tokenRepository;

    @Scheduled(cron = "0 4 * * * ?")
    public void deleteExpiredToken(){
        log.info("RefreshTokenScheduler - Delete All Expired Token");
        tokenRepository.deleteAllExpiredToken(LocalDateTime.now());
    }
}
