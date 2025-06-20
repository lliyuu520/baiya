package com.miguoma.by.common.cache;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Component;

import com.miguoma.by.common.utils.encode.WebBase62;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 二维码缓存
 *
 * @author liliangyu
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class QrCodeCache {
    /**
     * 二维码缓存key
     */
    private static final String QR_CODE_CACHE = "QR_CODE_CACHE:{}";

    /**
     * 批量处理大小
     */
    private static final int BATCH_SIZE = 10000;
    /**
     * 二维码数量
     */
    private static final int MAX_NUM = 5000000;
    /**
     * 二维码后缀长度
     */
    private static final int QR_CODE_SUFFIX_LENGTH = 5;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 初始化填充二维码
     * 使用pipeline批量处理，提高性能
     */
    public void init() {
        final LocalDate beginDateTime = LocalDateTimeUtil.now().toLocalDate();
        final LocalDate endDateTime = beginDateTime.plusDays(3);
        LocalDate localDate = beginDateTime;
        while (localDate.isBefore(endDateTime)) {
            final String dayFormat = LocalDateTimeUtil.format(localDate, DatePattern.PURE_DATE_PATTERN);
            final String dayEncode = WebBase62.encode(Long.parseLong(dayFormat));
            // 补齐到5位，不足左侧补0，确保二维码总长10位
            final String encodePrefix = StrUtil.fillBefore(dayEncode, '0', QR_CODE_SUFFIX_LENGTH);
            final String key = StrUtil.format(QR_CODE_CACHE, dayFormat);
            if (redisTemplate.hasKey(key)) {
                log.info("当日{}二维码已存在，跳过",dayFormat);
                localDate = localDate.plusDays(1);
                continue;
            }

            redisTemplate.expire(key, 3, TimeUnit.DAYS);
            long startTime = System.currentTimeMillis();
            log.info("初始化二维码缓存...");
            ArrayList<String> qrCodeList = new ArrayList<>(MAX_NUM);

            for (int i = 0; i < MAX_NUM; i++) {
                final String randomString = RandomUtil.randomString(QR_CODE_SUFFIX_LENGTH);
                qrCodeList.add(encodePrefix + randomString);
            }
            int startIndex = 0;
            int endIndex = BATCH_SIZE;
            SetOperations<String, String> opsForSet = redisTemplate.opsForSet();
            final int times = MAX_NUM / BATCH_SIZE;
            for (int i = 0; i < times; i++) {
                final List<String> sub = CollUtil.sub(qrCodeList, startIndex, endIndex);
                if (CollUtil.isNotEmpty(sub)) {
                    opsForSet.add(key, ArrayUtil.toArray(sub, String.class));
                }
                startIndex = endIndex;
                endIndex = endIndex + BATCH_SIZE;
            }
            Long size = opsForSet.size(key);
            long costTime = System.currentTimeMillis() - startTime;
            log.info("初始化二维码计划数量{}个，实际数量{}个，耗时{}ms", MAX_NUM, size, costTime);
            localDate = localDate.plusDays(1);
        }
    }

    /**
     * 获取指定数量的二维码 需要移除
     * 
     */
    public List<String> getQrCode(Integer num) {
        final LocalDateTime now = LocalDateTimeUtil.now();
        final String format = LocalDateTimeUtil.format(now, DatePattern.PURE_DATE_PATTERN);
        String key = StrUtil.format(QR_CODE_CACHE, format);
        final SetOperations<String, String> operations = redisTemplate.opsForSet();
        return operations.pop(key, num);
    }

}
