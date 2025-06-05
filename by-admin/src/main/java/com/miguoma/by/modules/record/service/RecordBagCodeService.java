 package com.miguoma.by.modules.record.service;

import com.miguoma.by.common.base.service.BaseService;
import com.miguoma.by.modules.record.entity.RecordBagCode;

import java.util.List;

 /**
 * 二维码关联服务接口
 * 提供二维码关联相关的业务操作
 *
 * @author liliangyu
 */
public interface RecordBagCodeService extends BaseService<RecordBagCode> {

     /**
      *     * 根据箱码集合获取二维码关联集合
      * @param boxCode
      * @return
      */
     List<RecordBagCode> listByBoxCode(String boxCode);
 } 
