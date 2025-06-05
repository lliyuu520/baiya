package com.miguoma.by.modules.production.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.miguoma.by.common.annotation.SysLogCut;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.enums.SysLogModuleEnums;
import com.miguoma.by.common.enums.SysLogTypeEnums;
import com.miguoma.by.common.utils.Result;
import com.miguoma.by.modules.production.dto.ProductionTeamDTO;
import com.miguoma.by.modules.production.entity.ProductionTeam;
import com.miguoma.by.modules.production.query.ProductionTeamQuery;
import com.miguoma.by.modules.production.service.ProductionTeamService;
import com.miguoma.by.modules.production.vo.ProductionTeamVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 班组视图
 *
 * @author liliangyu
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/production/team")
public class ProductionTeamController {

    private final ProductionTeamService productionTeamService;

    /**
     * 分页
     *
     * @param productionTeamQuery
     * @return
     */
    @GetMapping("/page")
    @SysLogCut(type = SysLogTypeEnums.SELECT, module = SysLogModuleEnums.TEAM)
    @SaCheckPermission(value = "production:team:page")
    public Result<PageVO<ProductionTeamVO>> page(ProductionTeamQuery productionTeamQuery) {
        PageVO<ProductionTeamVO> pageResult = productionTeamService.pageVO(productionTeamQuery);
        return Result.ok(pageResult);
    }

    /**
     * 新增
     *
     * @param teamDTO
     */
    @PostMapping
    @SysLogCut(type = SysLogTypeEnums.INSERT, module = SysLogModuleEnums.TEAM)
    @SaCheckPermission(value = "production:team:save")
    public Result<String> save(@RequestBody ProductionTeamDTO teamDTO) {
        productionTeamService.saveOne(teamDTO);
        return Result.ok();
    }

    /**
     * 编辑
     *
     * @param teamDTO
     * @return
     */
    @PutMapping
    @SysLogCut(type = SysLogTypeEnums.UPDATE, module = SysLogModuleEnums.TEAM)
    @SaCheckPermission(value = "production:team:update")
    public Result<String> update(@RequestBody ProductionTeamDTO teamDTO) {
        productionTeamService.updateOne(teamDTO);
        return Result.ok();
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    @SysLogCut(type = SysLogTypeEnums.DELETE, module = SysLogModuleEnums.TEAM)
    @SaCheckPermission(value = "production:team:delete")
    public Result<String> delete(Long id) {
        productionTeamService.deleteById(id);
        return Result.ok();
    }

    /**
     * 详情
     */
    @GetMapping("/info")
    @SysLogCut(type = SysLogTypeEnums.VIEW, module = SysLogModuleEnums.TEAM)
    @SaCheckPermission(value = "production:team:info")
    public Result<ProductionTeam> info(Long id) {
        ProductionTeam productionTeam = productionTeamService.getOneById(id);
        return Result.ok(productionTeam);
    }

    /**
     * 获取班组列表
     *
     * @return 班组列表
     */
    @GetMapping("/list")
    public Result<List<ProductionTeam>> list() {
        List<ProductionTeam> productionTeamList = productionTeamService.list();
        return Result.ok(productionTeamList);
    }


}
