package com.miguoma.by.modules.system.service;

import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.BaseService;
import com.miguoma.by.modules.system.entity.SysLog;
import com.miguoma.by.modules.system.query.SysLogQuery;
/**
 * 系统日志服务接口
 *
 * @author lliyuu520 lliyuu520@gmail.com
 */
public interface SysLogService extends BaseService<SysLog> {

  
    /**
     * 分页查询
     *
     * @param sysLogQuery 日志查询条件
     * @return 日志列表
     */
    PageVO<SysLog> pageVO(SysLogQuery sysLogQuery);
}
