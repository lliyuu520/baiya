package com.miguoma.by.modules.system.controller;

import com.miguoma.by.common.utils.Result;
import com.miguoma.by.modules.system.dto.SysTableFieldDTO;
import com.miguoma.by.modules.system.service.SysTableFieldService;
import com.miguoma.by.modules.system.vo.SysTableFieldVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字段配置
 *
 * @author liliangyu
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/sys/tableField")
public class SysTableFieldController {

    private final SysTableFieldService sysTableFieldService;


    /**
     * 根据表配置id查询字段配置信息
     *
     * @param tableConfigId
     * @return
     */
    @GetMapping("/infoByTableConfigId")
    public Result<List<String>> infoByTableConfigId(Long tableConfigId) {
        final List<String> data = sysTableFieldService.getOneByTableConfigId(tableConfigId);
        return Result.ok(data);
    }


    /**
     * 新增字段配置
     *
     * @param sysTableFieldDTO 字段配置信息
     * @return 操作结果
     */
    @PostMapping
    public Result<String> save(@RequestBody SysTableFieldDTO sysTableFieldDTO) {
        sysTableFieldService.saveOne(sysTableFieldDTO);
        return Result.ok();
    }

    /**
     * listByTableConfigId
     *
     * @param tableConfigId 表配置id
     * @return 字段配置信息
     */
    @GetMapping("/listByTableConfigId")
    public Result<List<SysTableFieldVO>> listByTableConfigId(Long tableConfigId) {
        final List<SysTableFieldVO> data = sysTableFieldService.listByTableConfigId(tableConfigId);
        return Result.ok(data);
    }
}
