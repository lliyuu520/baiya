package com.miguoma.by.modules.production.vo;

import com.miguoma.by.modules.production.entity.ProductionFactory;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 工厂视图对象
 * 用于前端展示
 *
 * @author liliangyu
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductionFactoryVO extends ProductionFactory {
}
