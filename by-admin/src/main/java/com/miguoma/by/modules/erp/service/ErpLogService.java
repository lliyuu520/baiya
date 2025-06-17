package com.miguoma.by.modules.system.service;

import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.BaseService;
import com.miguoma.by.modules.system.entity.ErpLog;
import com.miguoma.by.modules.system.query.ErpLogQuery;

/**
 * 系统日志服务接口
 *
 * @author lliyuu520 lliyuu520@gmail.com
 */
public interface ErpLogService extends BaseService<ErpLog> {

  
    /**
     * 分页查询
     *
     * @param sysLogQuery 日志查询条件
     * @return 日志列表
     */
    PageVO<ErpLog> pageVO(ErpLogQuery sysLogQuery);
}
