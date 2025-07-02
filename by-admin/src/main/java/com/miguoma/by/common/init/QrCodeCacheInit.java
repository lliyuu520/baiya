package com.miguoma.by.common.init;

import com.miguoma.by.common.cache.QrCodeCache;
import com.miguoma.by.modules.system.service.SysCodeRuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 初始化二维码缓存
 *
 * @author liliangyu
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class QrCodeCacheInit implements CommandLineRunner {
    private final QrCodeCache qrCodeCache;

    private final SysCodeRuleService sysCodeRuleService;

    /**
     * 初始化二维码缓存
     */
    @Override
    public void run(String... args) throws Exception {
        qrCodeCache.init();
//        sysCodeRuleService.handleData();
    }
}
