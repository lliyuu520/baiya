package com.miguoma.by.modules.production.dto;

import com.miguoma.by.modules.production.entity.ProductionProduct;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 产品数据传输对象
 * 用于前后端数据传输
 *
 * @author liliangyu
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductionProductDTO extends ProductionProduct {
}