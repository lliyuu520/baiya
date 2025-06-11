package com.miguoma.by.modules.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.miguoma.by.common.annotation.SysLogCut;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.enums.SysLogModuleEnums;
import com.miguoma.by.common.enums.SysLogTypeEnums;
import com.miguoma.by.common.utils.Result;
import com.miguoma.by.modules.system.dto.SysCodeRuleDTO;
import com.miguoma.by.modules.system.query.SysCodeRuleQuery;
import com.miguoma.by.modules.system.service.SysCodeRuleService;
import com.miguoma.by.modules.system.vo.SysCodeRuleVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 编码规则视图控制器 提供编码规则相关的增删改查接口
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/sys/codeRule")
public class CodeRuleController {

    private final SysCodeRuleService sysCodeRuleService;

    /**
     * 分页查询编码规则列表
     *
     * @param sysCodeRuleQuery 查询条件
     * @return 分页结果
     */
    @GetMapping("/page")
    @SysLogCut(type = SysLogTypeEnums.PAGE, module = SysLogModuleEnums.CODE_RULE)
    @SaCheckPermission(value = "sys:codeRule:page")
    public Result<PageVO<SysCodeRuleVO>> page(SysCodeRuleQuery sysCodeRuleQuery) {
        PageVO<SysCodeRuleVO> pageResult = sysCodeRuleService.pageVO(sysCodeRuleQuery);
        return Result.ok(pageResult);
    }

    /**
     * 获取编码规则列表
     * @return
     */
    @GetMapping("/list")
    public Result<List<SysCodeRuleVO>> list() {
        List<SysCodeRuleVO> list = sysCodeRuleService.listVO();
        return Result.ok(list);
    }

    /**
     * 新增编码规则
     *
     * @param sysCodeRuleDTO 编码规则信息
     * @return 操作结果
     */
    @PostMapping
    @SysLogCut(type = SysLogTypeEnums.INSERT, module = SysLogModuleEnums.CODE_RULE)
    @SaCheckPermission(value = "sys:codeRule:save")
    public Result<String> save(@RequestBody SysCodeRuleDTO sysCodeRuleDTO) {
        
        sysCodeRuleService.saveOne(sysCodeRuleDTO);
        return Result.ok();
    }

    /**
     * 编辑编码规则
     *
     * @param sysCodeRuleDTO 编码规则信息
     * @return 操作结果
     */
    @PutMapping
    @SysLogCut(type = SysLogTypeEnums.UPDATE, module = SysLogModuleEnums.CODE_RULE)
    @SaCheckPermission(value = "sys:codeRule:update")
    public Result<String> update(@RequestBody SysCodeRuleDTO sysCodeRuleDTO) {
        sysCodeRuleService.updateOne(sysCodeRuleDTO);
        return Result.ok();
    }

    /**
     * 删除编码规则
     *
     * @param id 编码规则ID
     * @return 操作结果
     */
    @DeleteMapping("/delete")
    @SysLogCut(type = SysLogTypeEnums.DELETE, module = SysLogModuleEnums.CODE_RULE)
    @SaCheckPermission(value = "sys:codeRule:delete")
    public Result<String> delete(Long id) {
        sysCodeRuleService.deleteById(id);
        return Result.ok();
    }

    /**
     * 获取编码规则详情
     *
     * @param id 编码规则ID
     * @return 编码规则详情
     */
    @GetMapping("/info")
    @SysLogCut(type = SysLogTypeEnums.PAGE, module = SysLogModuleEnums.CODE_RULE)
    @SaCheckPermission(value = "sys:codeRule:info")
    public Result<SysCodeRuleVO> info(Long id) {
        SysCodeRuleVO codeRuleVO = sysCodeRuleService.getOneById(id);
        return Result.ok(codeRuleVO);
    }

    /**
     * 设置当前编码规则
     *
     * @param id 编码规则ID
     * @return 操作结果
     */
    @PostMapping("/setCurrent")
    @SysLogCut(type = SysLogTypeEnums.UPDATE, module = SysLogModuleEnums.CODE_RULE)
    @SaCheckPermission(value = "sys:codeRule:update")
    public Result<String> setCurrent(Long id) {
        sysCodeRuleService.setCurrentCodeRule(id);
        return Result.ok();
    }
}
