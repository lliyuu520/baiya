package com.miguoma.by.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.miguoma.by.common.satoken.user.UserDetail;
import com.miguoma.by.common.utils.SysUserUtil;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * mybatis-plus 自动填充字段
 *
 * @author lliyuu520 lliyuu520@gmail.com
 */
public class FieldMetaObjectHandler implements MetaObjectHandler {
    private final static String CREATE_TIME = "createTime";
    private final static String CREATOR = "creator";
    private final static String UPDATE_TIME = "updateTime";
    private final static String UPDATER = "updater";
    private final static String VERSION = "version";
    private final static String DELETED = "deleted";

    @Override
    public void insertFill(final MetaObject metaObject) {
        final UserDetail user = SysUserUtil.getUserInfo();
        final Date date = new Date();

        // 创建者
        this.strictInsertFill(metaObject, FieldMetaObjectHandler.CREATOR, Long.class, user.getId());
        // 创建时间
        this.strictInsertFill(metaObject, FieldMetaObjectHandler.CREATE_TIME, Date.class, date);
        // 更新者
        this.strictInsertFill(metaObject, FieldMetaObjectHandler.UPDATER, Long.class, user.getId());
        // 更新时间
        this.strictInsertFill(metaObject, FieldMetaObjectHandler.UPDATE_TIME, Date.class, date);
        // 版本号
        this.strictInsertFill(metaObject, FieldMetaObjectHandler.VERSION, Integer.class, 0);
        // 删除标识
        this.strictInsertFill(metaObject, FieldMetaObjectHandler.DELETED, Integer.class, 0);
    }

    @Override
    public void updateFill(final MetaObject metaObject) {
        // 更新者
        this.strictUpdateFill(metaObject, FieldMetaObjectHandler.UPDATER, Long.class, SysUserUtil.getUserInfo().getId());
        // 更新时间
        this.strictUpdateFill(metaObject, FieldMetaObjectHandler.UPDATE_TIME, Date.class, new Date());
    }
}
