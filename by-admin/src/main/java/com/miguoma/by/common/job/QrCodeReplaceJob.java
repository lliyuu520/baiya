package com.miguoma.by.common.job;

import com.miguoma.by.modules.record.service.RecordQrCodeReplaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
/*
 * @author: 
 * @date: 2025-06-14 10:00:00
 * @description: 二维码替换任务
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class QrCodeReplaceJob {

    private final RecordQrCodeReplaceService recordQrCodeReplaceService;

    /**
     * 每5分钟执行一次，处理等待状态的二维码替换记录
     */
    @Scheduled(fixedRate = 300000)
    public void processWaitingRecords() {
        log.info("开始处理未处理状态的二维码替换记录");

        recordQrCodeReplaceService.HandleNotHandleData();

        log.info("结束处理未处理状态的二维码替换记录");


    }
}