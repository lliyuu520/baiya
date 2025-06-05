package com.miguoma.by.modules.production.vo;

import com.miguoma.by.modules.production.entity.ProductionShift;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 产线视图对象
 * 用于前端展示
 *
 * @author liliangyu
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductionShiftVO extends ProductionShift {
}
