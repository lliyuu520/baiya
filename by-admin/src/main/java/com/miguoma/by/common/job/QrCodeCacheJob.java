package com.miguoma.by.common.job;

import com.miguoma.by.common.cache.QrCodeCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时填充二维码缓存
 * 
 * @author liliangyu
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class QrCodeCacheJob  {
    private final QrCodeCache qrCodeCache;

    /**
     *
     * 每小时执行一次
     */
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void fillCache(){
        qrCodeCache.init();
    }
}
