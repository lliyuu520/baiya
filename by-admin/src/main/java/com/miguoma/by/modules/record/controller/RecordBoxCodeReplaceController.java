package com.miguoma.by.modules.record.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miguoma.by.common.annotation.SysLogCut;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.enums.SysLogModuleEnums;
import com.miguoma.by.common.enums.SysLogTypeEnums;
import com.miguoma.by.common.utils.Result;
import com.miguoma.by.modules.record.entity.RecordBoxCodeReplace;
import com.miguoma.by.modules.record.query.RecordBoxCodeReplaceQuery;
import com.miguoma.by.modules.record.service.RecordBoxCodeReplaceService;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;

/**
 * 箱码替换记录控制器
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/record/boxCodeReplace")
public class RecordBoxCodeReplaceController {

    private final RecordBoxCodeReplaceService recordBoxCodeReplaceService;

    /**
     * 分页查询
     *
     * @param query 查询条件
     * @return 分页结果
     */
    @GetMapping("/page")
    @SysLogCut(type = SysLogTypeEnums.PAGE, module = SysLogModuleEnums.RECORD_BOX_CODE_REPLACE)
    @SaCheckPermission(value = "record:boxCodeReplace:page")
    public Result<PageVO<RecordBoxCodeReplace>> page(RecordBoxCodeReplaceQuery query) {
        PageVO<RecordBoxCodeReplace> pageResult = recordBoxCodeReplaceService.pageVO(query);
        return Result.ok(pageResult);
    }
}