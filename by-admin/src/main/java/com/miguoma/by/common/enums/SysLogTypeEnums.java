package com.miguoma.by.common.enums;

import lombok.Getter;

/**
 * 日志类型枚举类，定义了系统中不同操作的日志类型。
 *
 * @author liliangyu
 */
@Getter
public enum SysLogTypeEnums {

    /**
     * 新增操作日志类型，用于记录新增数据的操作。
     */
    INSERT("新增"),

    /**
     * 修改操作日志类型，用于记录修改数据的操作。
     */
    UPDATE("修改"),
    /**
     * 修改密码
     */
    UPDATE_PASSWORD("修改密码"),

    /**
     * 删除操作日志类型，用于记录删除数据的操作。
     */
    DELETE("删除"),

    /**
     * 查询操作日志类型，用于记录查询数据的操作。
     */
    PAGE("查询"),
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
    CANCEL_REWORK("撤销返工"),

    /**
     * 配置规则
     */
    CONFIG_CODE_RULE("配置编码规则"),
    /**
     * 配置别名
     */
    CONFIG_ALIAS("配置别名"),
    /**
     * 拉码
     */
    PULL_CODE("拉码"),
    /**
     * 上传码
     */
    UPLOAD_CODE("上传码"),

    /**
     * 拉取订单
     */
    ORDER_LIST("拉取订单"),
    /**
     * 登录
     */
    LOGIN("登录"),
    /**
     * 登出
     */
    LOGOUT("登出"),
    /**
     * 上传APK
     */
    UPLOAD_APK("上传APK"),

    /**
     * 验证密码
     */
    VERIFY_PASSWORD("验证密码"),
    /**
     * 客户端绑定
     */
    CLIENT_BIND("客户端绑定"),
    /**
     * 监控上传
     */
    UPLOAD_MONITOR("监控上传");

    private final String desc;

    SysLogTypeEnums(String desc) {
        this.desc = desc;
    }
}
