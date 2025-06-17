package com.miguoma.by.modules.record.service;

import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.BaseService;
import com.miguoma.by.modules.client.dto.RecordQrCodeReplaceDTO;
import com.miguoma.by.modules.record.entity.RecordQrCodeReplace;
import com.miguoma.by.modules.record.query.RecordQrCodeReplaceQuery;

/**
 * 二维码替换记录服务接口
 */
public interface RecordQrCodeReplaceService extends BaseService<RecordQrCodeReplace> {

    /**
     * 分页查询
     *
     * @param query 查询条件
     * @return 分页结果
     */
    PageVO<RecordQrCodeReplace> pageVO(RecordQrCodeReplaceQuery query);


    /**
     * 保存一条记录
     *
     * @param recordQrCodeReplaceDTO 替换记录DTO
     */
    String saveOne(RecordQrCodeReplaceDTO recordQrCodeReplaceDTO);

    /**
     *  处理未处理的记录
     * @return
     */
   void handleNotHandleData();

}