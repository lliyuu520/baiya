package com.miguoma.by.common.cache;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 订单箱标缓存
 *
 * @author liliangyu
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class QrCodeCache {
    /**
     * 订单箱标缓存key
     */
    private static final String QR_CODE_CACHE = "QR_CODE_CACHE:{}";


    /**
     * 批量处理大小
     */
    private static final int BATCH_SIZE = 1000;
    /**
     * 二维码数量
     */
    private static final int MAX_NUM = 5000000;


    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 初始化填充二维码
     * 使用pipeline批量处理，提高性能
     */
    public void init() {
        final LocalDateTime now = LocalDateTimeUtil.now();
        final String format = LocalDateTimeUtil.format(now, DatePattern.NORM_DATE_PATTERN);
        String key = StrUtil.format(QR_CODE_CACHE, format);

        if (redisTemplate.hasKey(key)) {
            log.info("QR code cache already exists, skip initialization");
            return;
        }
        redisTemplate.expire(key, 3, TimeUnit.DAYS);
        long startTime = System.currentTimeMillis();
        log.info("Initializing QR code cache...");
        ArrayList<String> qrCodeList = new ArrayList<>(MAX_NUM);

        for (int i = 0; i < MAX_NUM; i++) {
            qrCodeList.add(RandomUtil.randomString(5));
        }
        int startIndex = 0;
        int endIndex = BATCH_SIZE;
        final int times = MAX_NUM / BATCH_SIZE;
        for (int i = 0; i < times; i++) {
            final List<String> sub = CollUtil.sub(qrCodeList, startIndex, endIndex);
            if (CollUtil.isNotEmpty(sub)) {
                redisTemplate.opsForSet().add(key, ArrayUtil.toArray(sub, String.class));
            }
            startIndex = endIndex;
            endIndex = endIndex + BATCH_SIZE;
        }

        long costTime = System.currentTimeMillis() - startTime;
        log.info("初始化 QR code cache with {} codes in {}ms", MAX_NUM, costTime);
    }

    /**
     * 获取指定数量的二维码 需要移除
     */
    public List<String> getQrCode(Integer num) {
        final LocalDateTime now = LocalDateTimeUtil.now();
        final String format = LocalDateTimeUtil.format(now, DatePattern.NORM_DATE_PATTERN);
        String key = StrUtil.format(QR_CODE_CACHE, format);
        final SetOperations<String, String> operations = redisTemplate.opsForSet();
        return operations.pop(key, num);
    }
    
    


}
