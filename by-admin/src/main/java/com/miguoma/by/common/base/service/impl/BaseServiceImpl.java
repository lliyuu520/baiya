package com.miguoma.by.common.base.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miguoma.by.common.base.query.BaseQuery;
import com.miguoma.by.common.base.service.BaseService;


/**
 * 基础服务类，所有Service都要继承
 *
 * @author lliyuu520 lliyuu520@gmail.com
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {


    /**
     * 获取分页对象
     *
     * @param baseQuery 分页参数
     */
    protected IPage<T> getPage(final BaseQuery baseQuery) {
        final Page<T> page = new Page<>(baseQuery.getPage(), baseQuery.getLimit());

        // 排序
        if (StringUtils.isNotBlank(baseQuery.getOrder())) {
            if (baseQuery.isAsc()) {
                return page.addOrder(OrderItem.asc(baseQuery.getOrder()));
            } else {
                return page.addOrder(OrderItem.desc(baseQuery.getOrder()));
            }
        }

        return page;
    }


}
