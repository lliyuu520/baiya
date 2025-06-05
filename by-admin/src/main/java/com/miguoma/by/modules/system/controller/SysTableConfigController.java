package com.miguoma.by.modules.system.controller;

import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.utils.Result;
import com.miguoma.by.modules.system.dto.SysTableConfigDTO;
import com.miguoma.by.modules.system.entity.SysTableConfig;
import com.miguoma.by.modules.system.query.SysTableConfigQuery;
import com.miguoma.by.modules.system.service.SysTableConfigService;
import com.miguoma.by.modules.system.vo.SysTableConfigVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 标配置
 *
 * @author liliangyu
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/sys/tableConfig")
public class SysTableConfigController {

    private final SysTableConfigService sysTableConfigService;

    /**
     * 分页查询标配置列表
     *
     * @param sysTableConfigQuery 查询条件
     * @return 分页结果
     */
    @GetMapping("/page")
    public Result<PageVO<SysTableConfigVO>> page(SysTableConfigQuery sysTableConfigQuery) {
        PageVO<SysTableConfigVO> pageResult = sysTableConfigService.pageVO(sysTableConfigQuery);
        return Result.ok(pageResult);
    }

    /**
     * list
     */
    @GetMapping("/list")
    public Result<List<SysTableConfig>> list() {
        final List<SysTableConfig> list = sysTableConfigService.listAll();
        return Result.ok(list);
    }

    /**
     * 新增标配置
     *
     * @param tableConfigDTOList 标配置信息
     * @return 操作结果
     */
    @PostMapping
    public Result<String> save(@RequestBody List<SysTableConfigDTO> tableConfigDTOList) {
        sysTableConfigService.saveList(tableConfigDTOList);
        return Result.ok();
    }

    /**
     * 获取标配置详情
     *
     * @param id 标配置ID
     * @return 标配置详情
     */
    @GetMapping("/info")
    public Result<SysTableConfigVO> info(Long id) {
        SysTableConfigVO factoryVO = sysTableConfigService.getOneById(id);
        return Result.ok(factoryVO);
    }

    /**
     * 所有表结构VO
     *
     * @return
     */
    @GetMapping("/listAllVO")
    public Result<List<SysTableConfigVO>> listAllVO() {
        List<SysTableConfigVO> listVO = sysTableConfigService.listAllVO();
        return Result.ok(listVO);
    }


}
