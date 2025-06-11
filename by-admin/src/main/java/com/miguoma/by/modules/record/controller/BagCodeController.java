package com.miguoma.by.modules.record.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.miguoma.by.common.annotation.SysLogCut;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.enums.SysLogModuleEnums;
import com.miguoma.by.common.enums.SysLogTypeEnums;
import com.miguoma.by.common.utils.Result;
import com.miguoma.by.modules.record.query.RecordBagCodeQuery;
import com.miguoma.by.modules.record.service.RecordBagCodeService;
import com.miguoma.by.modules.record.vo.RecordBagCodeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 袋码记录控制器
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/record/bagCode")
public class BagCodeController {

    private final RecordBagCodeService recordBagCodeService;

    /**
     * 分页查询
     *
     * @param recordBagCodeQuery 查询条件
     * @return 分页结果
     */
    @GetMapping("/page")
    @SysLogCut(type = SysLogTypeEnums.PAGE, module = SysLogModuleEnums.CODE_RULE)
    @SaCheckPermission(value = "record:bagCode:page")
    public Result<PageVO<RecordBagCodeVO>> page(RecordBagCodeQuery recordBagCodeQuery) {
        PageVO<RecordBagCodeVO> pageResult = recordBagCodeService.pageVO(recordBagCodeQuery);
        return Result.ok(pageResult);
    }
}