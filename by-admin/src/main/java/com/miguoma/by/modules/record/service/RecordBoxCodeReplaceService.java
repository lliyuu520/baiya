package com.miguoma.by.modules.record.service;

import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.BaseService;
import com.miguoma.by.modules.client.dto.RecordBoxCodeReplaceDTO;
import com.miguoma.by.modules.record.entity.RecordBoxCodeReplace;
import com.miguoma.by.modules.record.query.RecordBoxCodeReplaceQuery;

/**
 * 箱码替换记录服务接口
 */
public interface RecordBoxCodeReplaceService extends BaseService<RecordBoxCodeReplace> {

    /**
     * 分页查询
     *
     * @param query 查询条件
     * @return 分页结果
     */
    PageVO<RecordBoxCodeReplace> pageVO(RecordBoxCodeReplaceQuery query);

    /**
     * 保存一条记录
     *
     * @param recordBoxCodeReplaceDTO 替换记录DTO
     */
    void saveOne(RecordBoxCodeReplaceDTO recordBoxCodeReplaceDTO);


}