package com.miguoma.by.modules.production.query;

import com.miguoma.by.common.base.query.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 生产部门查询对象
 * 用于封装工厂查询条件
 *
 * @author liliangyu
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductionDepartAndWorkshopQuery extends BaseQuery {
    

    /**
     * 名称
     */
    private String name;
/**父级名称 */
    private String parentName;


  
}
