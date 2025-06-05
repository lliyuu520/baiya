package com.miguoma.by.modules.production.dto;

import com.miguoma.by.modules.production.entity.ProductionShift;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 产线数据传输对象
 * 用于前后端数据传输
 *
 * @author liliangyu
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductionShiftDTO extends ProductionShift {
}
