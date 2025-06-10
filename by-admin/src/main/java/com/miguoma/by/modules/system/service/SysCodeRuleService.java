package com.miguoma.by.modules.system.service;

import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.BaseService;
import com.miguoma.by.modules.system.dto.SysCodeRuleDTO;
import com.miguoma.by.modules.system.entity.SysCodeRule;
import com.miguoma.by.modules.system.query.SysCodeRuleQuery;
import com.miguoma.by.modules.system.vo.SysCodeRuleVO;

import java.util.List;

/**
 * 编码规则服务接口
 *
 * @author liliangyu
 */
public interface SysCodeRuleService extends BaseService<SysCodeRule> {

    /**
     * 分页查询编码规则列表
     *
     * @param query 查询条件
     * @return 分页结果
     */
    PageVO<SysCodeRuleVO> pageVO(SysCodeRuleQuery query);

    /**
     * 新增编码规则
     *
     * @param dto 编码规则信息
     */
    void saveOne(SysCodeRuleDTO dto);

    /**
     * 编辑编码规则
     *
     * @param dto 编码规则信息
     */
    void updateOne(SysCodeRuleDTO dto);

    /**
     * 获取编码规则详情
     *
     * @param id 编码规则ID
     * @return 编码规则详情
     */
    SysCodeRuleVO getOneById(Long id);

    /**
     * 删除编码规则
     *
     * @param id 编码规则ID
     */
    void deleteById(Long id);

    /**
     * 设置当前编码规则
     * @param id
     */
    void setCurrentCodeRule(Long id);
    
    
    

    /**
     * 
     * 获取当前的编码规则
     * @return
     */
    SysCodeRuleVO getCurrentCodeRule();

    /**
     * 获取编码规则列表
     * @return
     */
    List<SysCodeRuleVO> listVO();
}
