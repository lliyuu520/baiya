package com.miguoma.by.modules.record.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.miguoma.by.common.annotation.SysLogCut;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.enums.SysLogModuleEnums;
import com.miguoma.by.common.enums.SysLogTypeEnums;
import com.miguoma.by.common.utils.Result;
import com.miguoma.by.modules.record.query.RecordBoxCodeQuery;
import com.miguoma.by.modules.record.service.RecordBoxCodeService;
import com.miguoma.by.modules.record.vo.RecordBoxCodeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 件码记录控制器
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/record/boxCode")
public class BoxCodeController {

    private final RecordBoxCodeService recordBoxCodeService;

    /**
     * 分页查询
     *
     * @param recordBoxCodeQuery 查询条件
     * @return 分页结果
     */
    @GetMapping("/page")
    @SysLogCut(type = SysLogTypeEnums.PAGE, module = SysLogModuleEnums.CODE_RULE)
    @SaCheckPermission(value = "record:boxCode:page")
    public Result<PageVO<RecordBoxCodeVO>> page(RecordBoxCodeQuery recordBoxCodeQuery) {
        PageVO<RecordBoxCodeVO> pageResult = recordBoxCodeService.pageVO(recordBoxCodeQuery);
        return Result.ok(pageResult);
    }
}