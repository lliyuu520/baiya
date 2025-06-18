package com.miguoma.by.modules.client.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.util.StrUtil;
import com.miguoma.by.common.annotation.SysLogCut;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.enums.SysLogModuleEnums;
import com.miguoma.by.common.enums.SysLogTypeEnums;
import com.miguoma.by.common.utils.Result;
import com.miguoma.by.modules.client.dto.PdaLoginDTO;
import com.miguoma.by.modules.client.dto.RecordBoxCodeReplaceDTO;
import com.miguoma.by.modules.client.dto.RecordQrCodeReplaceDTO;
import com.miguoma.by.modules.equipment.vo.EquipmentApkVO;
import com.miguoma.by.modules.record.entity.RecordBoxCodeReplace;
import com.miguoma.by.modules.record.entity.RecordQrCodeReplace;
import com.miguoma.by.modules.record.query.RecordBoxCodeReplaceQuery;
import com.miguoma.by.modules.record.query.RecordQrCodeReplaceQuery;
import com.miguoma.by.modules.record.service.RecordBoxCodeReplaceService;
import com.miguoma.by.modules.record.service.RecordBoxCodeService;
import com.miguoma.by.modules.record.service.RecordQrCodeReplaceService;
import com.miguoma.by.modules.record.service.RecordQrCodeService;
import com.miguoma.by.modules.record.vo.RecordBoxCodeVO;
import com.miguoma.by.modules.record.vo.RecordQrCodeVO;
import com.miguoma.by.modules.system.dto.SysAccountLoginDTO;
import com.miguoma.by.modules.equipment.service.EquipmentApkService;
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
@RequestMapping("/pda")
@RequiredArgsConstructor

public class PdaController {


    private final SysAuthService sysAuthService;

    private final RecordQrCodeReplaceService recordQrCodeReplaceService;

    private final RecordQrCodeService recordQrCodeService;
    private final RecordBoxCodeService recordBoxCodeService;

    private final RecordBoxCodeReplaceService recordBoxCodeReplaceService;
    private final EquipmentApkService equipmentApkService;

    /**
     * 查询当前最新版本号
     */
    @SaIgnore
    @GetMapping("/apk/latestVersionNo")
    public Result<Long> latestVersion() {
        final Long latestVersionNo = equipmentApkService.getLatestVersionNo();
        return Result.ok(latestVersionNo);
    }

    /**
     * 获取最新版本
     * @return
     */
    @SaIgnore
    @GetMapping("/apk/latest")
    public Result<EquipmentApkVO> latest() {
        final EquipmentApkVO sysApkVO = equipmentApkService.getLatest();
        return Result.ok(sysApkVO);
    }




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
    @SysLogCut(module = SysLogModuleEnums.RECORD_QR_CODE_REPLACE, type = SysLogTypeEnums.PAGE)
    @SaCheckLogin
    public Result<PageVO<RecordQrCodeReplace>> recordQrCodeReplacePage(RecordQrCodeReplaceQuery query) {
        final PageVO<RecordQrCodeReplace> recordQrCodeReplacePageVO = recordQrCodeReplaceService.pageVO(query);
        return Result.ok(recordQrCodeReplacePageVO);
    }

    /**
     * 二维码提交置换记录
     */
    @PostMapping("/recordQrCodeReplace/insert")
    @SysLogCut(module = SysLogModuleEnums.RECORD_QR_CODE_REPLACE, type = SysLogTypeEnums.INSERT)
    @SaCheckLogin
    public Result<String> recordQrCodeReplaceInsert(@RequestBody RecordQrCodeReplaceDTO recordQrCodeReplaceDTO) {
        final String s = recordQrCodeReplaceService.saveOne(recordQrCodeReplaceDTO);
        if (StrUtil.isNotBlank(s)) {
            return Result.error(s);
        }
        return Result.ok();

    }

    /**
     * 箱码置换记录
     */

    @GetMapping("/recordBoxCodeReplace/page")
    @SysLogCut(module = SysLogModuleEnums.RECORD_BOX_CODE_REPLACE, type = SysLogTypeEnums.PAGE)
    @SaCheckLogin
    public Result<PageVO<RecordBoxCodeReplace>> recordBoxCodeReplacePage(RecordBoxCodeReplaceQuery query) {
        final PageVO<RecordBoxCodeReplace> recordBoxCodeReplacePageVO = recordBoxCodeReplaceService.pageVO(query);
        return Result.ok(recordBoxCodeReplacePageVO);
    }

    /**
     * 箱码提交置换记录
     */
    @PostMapping("/recordBoxCodeReplace/insert")
    @SysLogCut(module = SysLogModuleEnums.RECORD_BOX_CODE_REPLACE, type = SysLogTypeEnums.INSERT)
    @SaCheckLogin
    public Result<String> recordBoxCodeReplaceInsert(@RequestBody RecordBoxCodeReplaceDTO recordBoxCodeReplaceDTO) {
        recordBoxCodeReplaceService.saveOne(recordBoxCodeReplaceDTO);
        return Result.ok();
    }


    /**
     * 查看二维码信息
     */
    @GetMapping("/recordQrCode/info")
    @SysLogCut(module = SysLogModuleEnums.RECORD_QR_CODE, type = SysLogTypeEnums.VIEW)
    @SaCheckLogin
    public Result<RecordQrCodeVO> recordQrCodeInfo(String code) {
        final RecordQrCodeVO recordQrCodeReplace = recordQrCodeService.getVOByCode(code);
        return Result.ok(recordQrCodeReplace);
    }


    /**
     * 查看箱码信息
     */
    @GetMapping("/recordBoxCode/info")
    @SysLogCut(module = SysLogModuleEnums.RECORD_BOX_CODE, type = SysLogTypeEnums.VIEW)
    @SaCheckLogin
    public Result<RecordBoxCodeVO> recordBoxCodeInfo(String code) {
        final RecordBoxCodeVO recordQrCodeReplace = recordBoxCodeService.getVOByCode(code);
        return Result.ok(recordQrCodeReplace);
    }




}
