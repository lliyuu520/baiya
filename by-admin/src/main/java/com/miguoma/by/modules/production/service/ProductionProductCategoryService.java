package com.miguoma.by.modules.production.service;

import java.util.List;

import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.BaseService;
import com.miguoma.by.modules.erp.dto.ErpProductCategoryDTO;
import com.miguoma.by.modules.production.dto.ProductionProductCategoryDTO;
import com.miguoma.by.modules.production.entity.ProductionProductCategory;
import com.miguoma.by.modules.production.query.ProductionProductCategoryQuery;
import com.miguoma.by.modules.production.vo.ProductionProductCategoryVO;

/**
 * 产品分类服务接口
 * 提供产品分类相关的业务操作
 *
 * @author liliangyu
 */
public interface ProductionProductCategoryService extends BaseService<ProductionProductCategory> {

    /**
     * 分页查询产品分类列表
     *
     * @param query 查询条件
     * @return 分页结果
     */
    PageVO<ProductionProductCategoryVO> pageVO(ProductionProductCategoryQuery query);

    /**
     * 新增产品分类
     *
     * @param dto 产品分类信息
     */
    void saveOne(ProductionProductCategoryDTO dto);

    /**
     * 编辑产品分类
     *
     * @param dto 产品分类信息
     */
    void updateOne(ProductionProductCategoryDTO dto);

    /**
     * 获取产品分类详情
     *
     * @param id 产品分类ID
     * @return 产品分类详情
     */
    ProductionProductCategoryVO getOneById(Long id);

    /**
     * 删除产品分类
     *
     * @param id 产品分类ID
     */
    void deleteById(Long id);

    /**
     * 根据父级编码查询产品分类列表
     *
     * @param parentCode 父级编码
     * @return 产品分类列表
     */
    List<ProductionProductCategory> listByParentCode(String parentCode);

    /**
     *  同步ERP产品分类信息
     * @param productCategoryList
     */
    void syncProductCategory(List<ErpProductCategoryDTO> productCategoryList);
}