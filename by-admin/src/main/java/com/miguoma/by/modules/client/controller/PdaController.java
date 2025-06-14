package com.miguoma.by.modules.client.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import com.miguoma.by.common.annotation.SysLogCut;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.enums.SysLogModuleEnums;
import com.miguoma.by.common.enums.SysLogTypeEnums;
import com.miguoma.by.common.utils.Result;
import com.miguoma.by.modules.client.dto.PdaLoginDTO;
import com.miguoma.by.modules.client.dto.RecordQrCodeReplaceDTO;
import com.miguoma.by.modules.record.entity.RecordQrCodeReplace;
import com.miguoma.by.modules.record.query.RecordQrCodeReplaceQuery;
import com.miguoma.by.modules.record.service.RecordQrCodeReplaceService;
import com.miguoma.by.modules.record.service.RecordQrCodeService;
import com.miguoma.by.modules.record.vo.RecordQrCodeVO;
import com.miguoma.by.modules.system.dto.SysAccountLoginDTO;
import com.miguoma.by.modules.system.service.SysAuthService;
import com.miguoma.by.modules.system.vo.SysTokenVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * pda控制器
 *
 * @author liliangyu
 */
@Slf4j
@RestController
@RequestMapping("/pad")
@RequiredArgsConstructor

public class PdaController {


    private final SysAuthService sysAuthService;

    private final RecordQrCodeReplaceService recordQrCodeReplaceService;
    private final RecordQrCodeService recordQrCodeService;

    /**
     * 登录
     * header 中携带 Authorization
     *
     * @param dto
     * @return
     */
    @SaIgnore
    @PostMapping("/login")
    @SysLogCut(module = SysLogModuleEnums.PDA, type = SysLogTypeEnums.LOGIN)
    public Result<String> login(@RequestBody PdaLoginDTO dto) {
        final String username = dto.getUsername();
        final String password = dto.getPassword();

        SysAccountLoginDTO sysAccountLoginDTO = new SysAccountLoginDTO();
        sysAccountLoginDTO.setUsername(username);
        sysAccountLoginDTO.setPassword(password);
        final SysTokenVO sysTokenVO = sysAuthService.loginByAccount(sysAccountLoginDTO);
        final String accessToken = sysTokenVO.getAccessToken();
        return Result.ok(accessToken);
    }

    /**
     * 二维码置换记录
     *
     * @param query 查询条件
     */
    @GetMapping("/recordQrCodeReplace/page")
    @SysLogCut(module = SysLogModuleEnums.PDA, type = SysLogTypeEnums.PAGE)
    public Result<PageVO<RecordQrCodeReplace>> recordQrCodeReplacePage(RecordQrCodeReplaceQuery query) {
        final PageVO<RecordQrCodeReplace> recordQrCodeReplacePageVO = recordQrCodeReplaceService.pageVO(query);
        return Result.ok(recordQrCodeReplacePageVO);
    }

    /**
     * 提交置换记录
     */
    @PostMapping("/recordQrCodeReplace/insert")
    @SysLogCut(module = SysLogModuleEnums.PDA, type = SysLogTypeEnums.INSERT)
    public Result<String> recordQrCodeReplaceInsert(@RequestBody RecordQrCodeReplaceDTO recordQrCodeReplaceDTO) {
        recordQrCodeReplaceService.saveOne(recordQrCodeReplaceDTO);
        return Result.ok();

    }

    /**
     * 查看二维码信息
     */
    @GetMapping("/recordQrcode/info")
    @SysLogCut(module = SysLogModuleEnums.RECORD_QR_CODE, type = SysLogTypeEnums.VIEW)
    public Result<RecordQrCodeVO> recordQrcodeInfo(String code) {
        final RecordQrCodeVO recordQrCodeReplace = recordQrCodeService.getVOByCode(code);
        return Result.ok(recordQrCodeReplace);
    }


}
