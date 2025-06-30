package com.miguoma.by.modules.production.strategy;

import cn.hutool.core.codec.Base32;
import cn.hutool.core.codec.Base62;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.miguoma.by.common.exception.BaseException;
import com.miguoma.by.common.utils.encode.WebBase62;
import com.miguoma.by.modules.system.enums.EncodeTypeEnums;

/**
 * 策略：处理成品产品编码字段
 */
public class FinishedProductCodeStrategy implements CodeFieldStrategy {
    @Override
    public String apply(CodeFieldContext context) {
        final String finishedProductCode = context.getFinishedProductCode();
        Integer indexBegin = context.getIndexBegin();
        Integer indexEnd = context.getIndexEnd();
        String encodeType = context.getEncodeType();

        String str = StrUtil.sub(finishedProductCode, indexBegin, indexEnd);
        long i;
        try {
            i = Long.parseLong(str);
        } catch (NumberFormatException e) {
            throw new BaseException("编码格式错误,请联系管理员");
        }

        if (EncodeTypeEnums.BASE_62.getCode().equals(encodeType)) {
            str = WebBase62.encode(i);
        }
        if (EncodeTypeEnums.BASE_32.getCode().equals(encodeType)) {
            str = Base32.encode(str);
        }
        return str;
    }
}