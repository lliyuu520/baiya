package com.miguoma.by.modules.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.utils.Result;
import com.miguoma.by.modules.system.entity.ErpLog;
import com.miguoma.by.modules.system.query.ErpLogQuery;
import com.miguoma.by.modules.system.service.ErpLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/erp/log")
@RequiredArgsConstructor
public class ErpLogController {

    private final ErpLogService sysLogService;

    /**
     * @param query
     * @return
     */
    @GetMapping("/page")
    @SaCheckPermission(value = "erp:log:page")
    public Result<PageVO<ErpLog>> page(ErpLogQuery query) {
        PageVO<ErpLog> page = sysLogService.pageVO(query);

        return Result.ok(page);
    }

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    @GetMapping("/info")
    @SaCheckPermission(value = "erp:log:info")
    public Result<ErpLog> info(Long id) {
        ErpLog sysLog = sysLogService.getById(id);

        return Result.ok(sysLog);
    }


}
