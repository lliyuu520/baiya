package com.miguoma.by.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.impl.BaseServiceImpl;
import com.miguoma.by.modules.system.entity.SysLog;
import com.miguoma.by.modules.system.mapper.SysLogMapper;
import com.miguoma.by.modules.system.query.SysLogQuery;
import com.miguoma.by.modules.system.service.SysLogService;
import org.springframework.stereotype.Service;

/**
 * 系统日志服务实现类
 *
 * @author lliyuu520 lliyuu520@gmail.com
 */
@Service
public class SysLogServiceImpl extends BaseServiceImpl<SysLogMapper, SysLog> implements SysLogService {


   

    /**
     * 分页查询
     *
     * @param sysLogQuery 日志查询条件
     * @return 日志列表
     */
    @Override
    public PageVO<SysLog> pageVO(SysLogQuery sysLogQuery) {

        final IPage<SysLog> page = page(getPage(sysLogQuery), buildQueryWrapper(sysLogQuery));
        return PageVO.of(page);
    }

    /**
     * 构建查询条件
     * @param sysLogQuery
     * @return
     */
   private  LambdaQueryWrapper<SysLog> buildQueryWrapper(SysLogQuery sysLogQuery){
       final LambdaQueryWrapper<SysLog> queryWrapper = Wrappers.lambdaQuery();
       queryWrapper.orderByDesc(SysLog::getId);
       return null;
   }
}
