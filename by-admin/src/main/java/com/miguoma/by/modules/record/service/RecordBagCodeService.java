package com.miguoma.by.modules.record.service;

import java.util.List;

import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.BaseService;
import com.miguoma.by.modules.record.entity.RecordBagCode;
import com.miguoma.by.modules.record.query.RecordBagCodeQuery;
import com.miguoma.by.modules.record.vo.RecordBagCodeVO;

/**
 * 袋码关联服务接口
 * 提供袋码关联相关的业务操作
 *
 * @author liliangyu
 */
public interface RecordBagCodeService extends BaseService<RecordBagCode> {

    /**
     * 根据箱码集合获取二维码关联集合
     * 
     * @param boxCode
     * @return
     */
    List<RecordBagCode> listByBoxCode(String boxCode);

    /**
     * 分页查询
     * 
     * @param recordBagCodeQuery 查询条件
     * @return 分页结果
     */
    PageVO<RecordBagCodeVO> pageVO(RecordBagCodeQuery recordBagCodeQuery);
}
