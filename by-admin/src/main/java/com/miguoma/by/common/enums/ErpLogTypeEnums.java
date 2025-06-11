package com.miguoma.by.common.enums;

import lombok.Getter;

/**
 * 日志类型枚举类，定义了系统中不同操作的日志类型。
 *
 * @author liliangyu
 */
@Getter
public enum ErpLogTypeEnums {

    /**
     * 新增操作日志类型，用于记录新增数据的操作。
     */
    INSERT("新增"),

    /**
     * 修改操作日志类型，用于记录修改数据的操作。
     */
    UPDATE("修改"),

    /**
     * 删除操作日志类型，用于记录删除数据的操作。
     */
    DELETE("删除"),

    /**
     * 查询操作日志类型，用于记录查询数据的操作。
     */
    SELECT("查询"),
    /**
     * 查看
     */
    VIEW("查看"),

    /**
     * 导出操作日志类型，用于记录导出数据的操作。
     */
    EXPORT("导出"),

    /**
     * 导入操作日志类型，用于记录导入数据的操作。
     */
    IMPORT("导入"),

    /**
     * 其他操作日志类型，用于记录不属于上述类别的其他操作。
     */
    OTHER("其他"),

    /**
     * 返工
     */
    REWORK("返工"),

    /**
     * 撤销返工
     */
    CANCEL_REWORK("撤销返工");

    private final String desc;

    ErpLogTypeEnums(String desc) {
        this.desc = desc;
    }
}
