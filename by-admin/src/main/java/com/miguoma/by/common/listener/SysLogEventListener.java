package com.miguoma.by.common.listener;

import com.miguoma.by.common.event.SysLogEvent;
import com.miguoma.by.modules.system.entity.SysLog;
import com.miguoma.by.modules.system.service.SysLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 日志事件监听器
 *
 * @author liliangyu
 */
@Component
@RequiredArgsConstructor
public class SysLogEventListener {

    private final SysLogService sysLogService;

    @Async
    @EventListener
    public void handleSysLogEvent(SysLogEvent event) {
        SysLog sysLog = event.getSysLog();
        sysLogService.save(sysLog);
    }
}
