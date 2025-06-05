package com.miguoma.by.common.event;

import com.miguoma.by.modules.system.entity.SysLog;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 操作日志事件
 *
 * @author liliangyu
 */
@Getter
public class SysLogEvent extends ApplicationEvent {

    private final SysLog sysLog;

    public SysLogEvent(SysLog sysLog) {
        super(sysLog);
        this.sysLog = sysLog;
    }
}
