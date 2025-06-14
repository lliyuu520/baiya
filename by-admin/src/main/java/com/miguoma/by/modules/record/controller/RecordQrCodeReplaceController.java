package com.miguoma.by.modules.record.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.miguoma.by.common.annotation.SysLogCut;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.enums.SysLogModuleEnums;
import com.miguoma.by.common.enums.SysLogTypeEnums;
import com.miguoma.by.common.utils.Result;
import com.miguoma.by.modules.record.entity.RecordQrCodeReplace;
import com.miguoma.by.modules.record.query.RecordQrCodeReplaceQuery;
import com.miguoma.by.modules.record.service.RecordQrCodeReplaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 二维码替换记录控制器
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/record/qrCodeReplace")
public class RecordQrCodeReplaceController {

    private final RecordQrCodeReplaceService recordQrCodeReplaceService;


    /**
     * 分页查询
     *
     * @param query 查询条件
     * @return 分页结果
     */
    @GetMapping("/page")
    @SysLogCut(type = SysLogTypeEnums.PAGE, module = SysLogModuleEnums.RECORD_QR_CODE_REPLACE)
    @SaCheckPermission(value = "record:qrCodeReplace:page")
    public Result<PageVO<RecordQrCodeReplace>> page(RecordQrCodeReplaceQuery query) {
        PageVO<RecordQrCodeReplace> pageResult = recordQrCodeReplaceService.pageVO(query);
        return Result.ok(pageResult);
    }
}