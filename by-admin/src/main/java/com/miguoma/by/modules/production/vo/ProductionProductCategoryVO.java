package com.miguoma.by.modules.production.vo;

import com.miguoma.by.modules.production.entity.ProductionProductCategory;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 产品分类视图对象
 * 用于前端展示
 *
 * @author liliangyu
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductionProductCategoryVO extends ProductionProductCategory {
}