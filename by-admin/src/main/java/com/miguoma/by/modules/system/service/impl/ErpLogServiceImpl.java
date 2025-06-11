package com.miguoma.by.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.impl.BaseServiceImpl;
import com.miguoma.by.modules.system.entity.ErpLog;
import com.miguoma.by.modules.system.mapper.ErpLogMapper;
import com.miguoma.by.modules.system.query.ErpLogQuery;
import com.miguoma.by.modules.system.service.ErpLogService;

import cn.hutool.core.util.StrUtil;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

/**
 * 系统日志服务实现类
 *
 * @author lliyuu520 lliyuu520@gmail.com
 */
@Service
public class ErpLogServiceImpl extends BaseServiceImpl<ErpLogMapper, ErpLog> implements ErpLogService {


   

    /**
     * 分页查询
     *
     * @param erpLogQuery 日志查询条件
     * @return 日志列表
     */
    @Override
    public PageVO<ErpLog> pageVO(ErpLogQuery erpLogQuery) {

        final IPage<ErpLog> page = page(getPage(erpLogQuery), buildQueryWrapper(erpLogQuery));
        return PageVO.of(page);
    }

    /**
     * 构建查询条件
     * @param erpLogQuery
     * @return
     */
   private  LambdaQueryWrapper<ErpLog> buildQueryWrapper(ErpLogQuery erpLogQuery){
       final LambdaQueryWrapper<ErpLog> queryWrapper = Wrappers.lambdaQuery();
       final String moduleName = erpLogQuery.getModuleName();
       if(StrUtil.isNotBlank(moduleName)){
           queryWrapper.eq(ErpLog::getModuleName,moduleName);
       }
       
       final Integer status = erpLogQuery.getStatus();
       if(status != null){
           queryWrapper.eq(ErpLog::getStatus,status);
       }
      
       LocalDateTime operateTimeBegin = erpLogQuery.getOperateTimeBegin();
       if (operateTimeBegin != null) {
           queryWrapper.ge(ErpLog::getOperateTime, operateTimeBegin);
       }
       LocalDateTime operateTimeEnd = erpLogQuery.getOperateTimeEnd();
       if (operateTimeEnd != null) {
           queryWrapper.le(ErpLog::getOperateTime, operateTimeEnd);
       }

       queryWrapper.orderByDesc(ErpLog::getId);
       return queryWrapper;
   }
}
