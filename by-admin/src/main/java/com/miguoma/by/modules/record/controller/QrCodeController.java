package com.miguoma.by.modules.record.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.miguoma.by.common.annotation.SysLogCut;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.enums.SysLogModuleEnums;
import com.miguoma.by.common.enums.SysLogTypeEnums;
import com.miguoma.by.common.utils.Result;
import com.miguoma.by.modules.record.query.RecordQrCodeQuery;
import com.miguoma.by.modules.record.service.RecordQrCodeService;
import com.miguoma.by.modules.record.vo.RecordQrCodeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 二维码记录控制器
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/record/qrCode")
public class QrCodeController {

    private final RecordQrCodeService recordQrCodeService;

    /**
     * 分页查询
     *
     * @param recordQrCodeQuery 查询条件
     * @return 分页结果
     */
    @GetMapping("/page")
    @SysLogCut(type = SysLogTypeEnums.PAGE, module = SysLogModuleEnums.CODE_RULE)
    @SaCheckPermission(value = "record:qrCode:page")
    public Result<PageVO<RecordQrCodeVO>> page(RecordQrCodeQuery recordQrCodeQuery) {
        PageVO<RecordQrCodeVO> pageResult = recordQrCodeService.pageVO(recordQrCodeQuery);
        return Result.ok(pageResult);
    }
}