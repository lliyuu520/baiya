package com.miguoma.by.modules.production.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.impl.BaseServiceImpl;
import com.miguoma.by.common.cache.QrCodeCache;
import com.miguoma.by.common.enums.BaseEncodeEnums;
import com.miguoma.by.common.enums.PullCodeEnums;
import com.miguoma.by.common.exception.BaseException;
import com.miguoma.by.common.utils.EncodeConvertUtils;
import com.miguoma.by.modules.client.dto.PullCodeDTO;
import com.miguoma.by.modules.client.dto.RecordCodeUploadDTO;
import com.miguoma.by.modules.client.vo.PullCodeVO;
import com.miguoma.by.modules.erp.dto.ErpOrderDTO;
import com.miguoma.by.modules.production.convert.ProductionOrderConvert;
import com.miguoma.by.modules.production.dto.ProductionOrderDTO;
import com.miguoma.by.modules.production.entity.ProductionDepartAndWorkshop;
import com.miguoma.by.modules.production.entity.ProductionOrder;
import com.miguoma.by.modules.production.entity.ProductionProduct;
import com.miguoma.by.modules.production.enums.ProductTypeEnum;
import com.miguoma.by.modules.production.mapper.*;
import com.miguoma.by.modules.production.query.ProductionOrderQuery;
import com.miguoma.by.modules.production.service.ProductionOrderService;
import com.miguoma.by.modules.production.strategy.BaseCodeFieldStrategy;
import com.miguoma.by.modules.production.strategy.CodeFieldContext;
import com.miguoma.by.modules.production.strategy.RandomStringUtil;
import com.miguoma.by.modules.production.strategy.impl.*;
import com.miguoma.by.modules.production.vo.ProductionOrderVO;
import com.miguoma.by.modules.record.entity.RecordBagCode;
import com.miguoma.by.modules.record.entity.RecordBoxCode;
import com.miguoma.by.modules.record.entity.RecordQrCode;
import com.miguoma.by.modules.record.service.RecordBagCodeService;
import com.miguoma.by.modules.record.service.RecordBoxCodeService;
import com.miguoma.by.modules.record.service.RecordQrCodeService;
import com.miguoma.by.modules.system.dto.SysCodeRuleDetail;
import com.miguoma.by.modules.system.entity.SysCodeRule;
import com.miguoma.by.modules.system.mapper.SysCodeRuleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 生产订单服务实现类
 *
 * @author liliangyu
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductionOrderServiceImpl extends BaseServiceImpl<ProductionOrderMapper, ProductionOrder>
        implements ProductionOrderService {

    // sourceField 策略注册表
    private static final Map<String, BaseCodeFieldStrategy> CODE_FIELD_STRATEGY_MAP = new HashMap<>();
    // 提取箱号Pattern为类字段
    private static final Pattern BOX_NO_PATTERN = Pattern.compile("\\{BOX_NO:(\\d+)\\}");
    private static final Pattern RANDOM_STRING_PATTERN = Pattern.compile("\\{(RANDOM_STRING:\\w+:\\d+)\\}");
    private static final Pattern RANDOM_STRING_GROUP_PATTERN = Pattern.compile("\\{RANDOM_STRING:([A-Z_]+):(\\d+)\\}");

    static {
        // 成品相关 sourceField 策略注册
        // 成品生产日期（如20240530）
        CODE_FIELD_STRATEGY_MAP.put("FINISHED_PRODUCTION_DATE", new FinishedProductionDateStrategy());
        // 成品车间编码（如CJ01）
        CODE_FIELD_STRATEGY_MAP.put("FINISHED_DEPART_CODE", new FinishedDepartCodeStrategy());
        // 成品产线编码（如CX01）
        CODE_FIELD_STRATEGY_MAP.put("FINISHED_WORKSHOP_CODE", new FinishedWorkshopCodeStrategy());
        // 成品班次编码（如A班、B班）
        CODE_FIELD_STRATEGY_MAP.put("FINISHED_TEAM_CODE", new FinishedTeamCodeStrategy());
        // 成品订单编码（如PO20240530001）
        CODE_FIELD_STRATEGY_MAP.put("FINISHED_ORDER_CODE", new FinishedOrderCodeStrategy());
        // 成品产品编码（如P20240530001）
        CODE_FIELD_STRATEGY_MAP.put("FINISHED_PRODUCT_CODE", new FinishedProductCodeStrategy());

        // 半成品相关 sourceField 策略注册
        // 半成品生产日期（如20240530）
        CODE_FIELD_STRATEGY_MAP.put("SEMI_FINISHED_PRODUCTION_DATE", new SemiFinishedProductionDateStrategy());
        // 半成品车间编码（如CJ02）
        CODE_FIELD_STRATEGY_MAP.put("SEMI_FINISHED_DEPART_CODE", new SemiFinishedDepartCodeStrategy());
        // 半成品产线编码（如CX02）
        CODE_FIELD_STRATEGY_MAP.put("SEMI_FINISHED_WORKSHOP_CODE", new SemiFinishedWorkshopCodeStrategy());
        // 半成品班次编码（如C班、D班）
        CODE_FIELD_STRATEGY_MAP.put("SEMI_FINISHED_TEAM_CODE", new SemiFinishedTeamCodeStrategy());
        // 半成品订单编码（如PO20240530002）
        CODE_FIELD_STRATEGY_MAP.put("SEMI_FINISHED_ORDER_CODE", new SemiFinishedOrderCodeStrategy());
        // 半成品产品编码（如P20240530002）
        CODE_FIELD_STRATEGY_MAP.put("SEMI_FINISHED_PRODUCT_CODE", new SemiFinishedProductCodeStrategy());

        // 通用/特殊 sourceField 策略注册
        // 随机字符串（如4位随机码）
        CODE_FIELD_STRATEGY_MAP.put("RANDOM_STRING", new RandomStringStrategy());
        // 箱号（如0001、0002）
        CODE_FIELD_STRATEGY_MAP.put("BOX_NO", new BoxNoStrategy());
        // 常量（自定义固定值）
        CODE_FIELD_STRATEGY_MAP.put("CONSTANT", new ConstantStrategy());
        // 指定箱号（如外部传入箱号）
        CODE_FIELD_STRATEGY_MAP.put("SPECIFY_BOX_NO", new SpecifyBoxNoStrategy());
    }

    private final SysCodeRuleMapper sysCodeRuleMapper;

    private final QrCodeCache qrCodeCache;
    private final ProductionProductMapper productionProductMapper;
    private final RecordBoxCodeService recordBoxCodeService;
    private final RecordQrCodeService recordQrCodeService;
    private final RecordBagCodeService recordBagCodeService;
    private final ProductionShiftMapper productionShiftMapper;
    private final ProductionTeamMapper productionTeamMapper;
    private final ProductionDepartAndWorkshopMapper productionDepartAndWorkshopMapper;

    /**
     * 分页查询生产订单列表
     *
     * @param query 查询条件
     * @return 分页结果
     */
    @Override
    public PageVO<ProductionOrderVO> pageVO(ProductionOrderQuery query) {
        IPage<ProductionOrderVO> page = baseMapper.pageVO(getPage(query), query);

        final List<ProductionOrderVO> records = page.getRecords();
        records.forEach(m -> {
            final Long id = m.getId();
            final String productType = m.getProductType();
            if (StrUtil.equals(ProductTypeEnum.FINISHED_PRODUCT.getCode(), productType)) {
                m.setBoxCodeCount(recordBoxCodeService.getCountByFinishedProductionOrderId(id));
            }
            if (StrUtil.equals(ProductTypeEnum.SEMI_FINISHED_PRODUCT.getCode(), productType)) {
                m.setBoxCodeCount(recordBoxCodeService.getCountBySemiFinishedProductionOrderId(id));
            }
        });

        return PageVO.of(page);
    }

    /**
     * 列表
     *
     * @param query
     * @return
     */
    @Override
    public List<ProductionOrderVO> listVO(ProductionOrderQuery query) {
        List<ProductionOrderVO> records = baseMapper.listVO(query);
        records.forEach(this::accept);
        return records;
    }

    /**
     * 新增生产订单
     *
     * @param dto 生产订单信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOne(ProductionOrderDTO dto) {
        ProductionOrder order = ProductionOrderConvert.INSTANCE.convertFromDTO(dto);
        save(order);
    }

    /**
     * 编辑生产订单
     *
     * @param dto 生产订单信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOne(ProductionOrderDTO dto) {
        ProductionOrder order = ProductionOrderConvert.INSTANCE.convertFromDTO(dto);
        updateById(order);
    }

    /**
     * 获取生产订单详情
     *
     * @param id 生产订单ID
     * @return 生产订单详情
     */
    @Override
    public ProductionOrderVO getOneById(Long id) {
        ProductionOrder order = getById(id);
        return ProductionOrderConvert.INSTANCE.convertToVO(order);
    }

    /**
     * 删除生产订单
     *
     * @param id 生产订单ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        removeById(id);
    }

    /**
     * 返工
     *
     * @param id 生产订单ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rework(Long id) {
        final ProductionOrder productionOrder = getById(id);
        if (productionOrder != null) {
            productionOrder.setReworkFlag(true);
            updateById(productionOrder);
        }
    }

    /**
     * 取消返工
     *
     * @param id 生产订单ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelRework(Long id) {
        final ProductionOrder productionOrder = getById(id);
        if (productionOrder != null) {
            productionOrder.setReworkFlag(false);
            updateById(productionOrder);
        }
    }

    /**
     * 刷新箱号
     *
     * @param productionOrder
     * @param boxNoBegin
     * @param boxCodeNum
     */
    private void refreshBoxNo(ProductionOrder productionOrder, Integer boxNoBegin, Integer boxCodeNum) {
        final Integer boxNo = productionOrder.getBoxNo();
        if (boxNoBegin <= boxNo) {
            throw new BaseException("起始箱号:{}小于当前箱号:{}", boxNoBegin, boxNo);
        }
        final Integer boxNoEnd = boxNoBegin + boxCodeNum - 1;
        productionOrder.setBoxNo(boxNoEnd);
        updateById(productionOrder);
    }

    /**
     * 拉码
     *
     * @param pullCodeDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PullCodeVO pullCode(PullCodeDTO pullCodeDTO) {
        final Long finishedProductOrderId = pullCodeDTO.getFinishedProductOrderId();
        final Long semiFinishedProductOrderId = pullCodeDTO.getSemiFinishedProductOrderId();
        final ProductionOrder finishedProductionOrder = getById(finishedProductOrderId);
        if (finishedProductionOrder == null) {
            log.error("成品订单不存在:{}", finishedProductOrderId);
            throw new BaseException("成品订单不存在:{}", finishedProductOrderId);
        }
        final ProductionOrder semiFinishedProductionOrder = getById(semiFinishedProductOrderId);
        if (semiFinishedProductionOrder == null) {
            log.error("半成品订单不存在:{}", semiFinishedProductOrderId);
            throw new BaseException("半成品订单不存在:{}", semiFinishedProductOrderId);
        }

        final String finishedProductCode = finishedProductionOrder.getProductCode();
        final ProductionProduct finishProductionProduct = productionProductMapper.getOneByCode(finishedProductCode);
        if (finishProductionProduct == null) {
            log.error("成品产品不存在:{}", finishedProductCode);
            throw new BaseException("成品产品不存在:{}", finishedProductCode);
        }
        final String semiFinishedProductCode = semiFinishedProductionOrder.getProductCode();
        final ProductionProduct semiFinishedProductionProduct = productionProductMapper
                .getOneByCode(semiFinishedProductCode);
        if (semiFinishedProductionProduct == null) {
            log.error("半成品产品不存在:{}", semiFinishedProductCode);
            throw new BaseException("半成品产品不存在:{}", semiFinishedProductCode);
        }

        // 按照成品产品的包装比例
        final Integer oneBoxPackageNum = finishProductionProduct.getOneBoxPackageNum();

        // 成品
        final String finishedOrderNo = finishedProductionOrder.getOrderNo();
        final LocalDate finishedProductionDate = finishedProductionOrder.getProductionDate();
        final String finishedProductionDepartCode = finishedProductionOrder.getProductionDepartCode();
        final String finishedProductionWorkshopCode = finishedProductionOrder.getProductionWorkshopCode();
        final String finishedTeamCode = semiFinishedProductionOrder.getProductionTeamCode();
        // 半成品
        final String semiFinishedOrderNo = semiFinishedProductionOrder.getOrderNo();
        final LocalDate semiFinishedProductionDate = semiFinishedProductionOrder.getProductionDate();
        final String semiFinishedProductionDepartCode = semiFinishedProductionOrder.getProductionDepartCode();
        final String semiFinishedProductionWorkshopCode = semiFinishedProductionOrder.getProductionWorkshopCode();
        final String semiFinishedTeamCode = semiFinishedProductionOrder.getProductionTeamCode();

        final ProductionDepartAndWorkshop fineshedProductionDepartAndWorkshop = productionDepartAndWorkshopMapper
                .getOneByCode(finishedProductionWorkshopCode);
        final Long finishedCodeRuleId = fineshedProductionDepartAndWorkshop.getCodeRuleId();
        SysCodeRule finishedCodeRule = null;
        if (finishedCodeRuleId != null) {
            finishedCodeRule = sysCodeRuleMapper.selectById(finishedCodeRuleId);

        } else {
            finishedCodeRule = sysCodeRuleMapper.selectEnabled();

        }
        if (finishedCodeRule == null) {
            throw new BaseException("编码规则不存在");
        }
        // 箱号开始
        final int boxNoBegin = pullCodeDTO.getBoxNoBegin();
        // 箱号数量
        final int boxCodeNum = pullCodeDTO.getBoxCodeNum();
        // 二维码数量
        final int qrCodeNum = oneBoxPackageNum * (boxCodeNum + 2);
        // 拉码类型
        final String type = pullCodeDTO.getType();
        final Long ruleId = finishedCodeRule.getId();
        // 二维码url前缀
        final String qrCodeUrlPrefix = finishedCodeRule.getQrCodeUrlPrefix();
        // 刷新当前箱号
        refreshBoxNo(finishedProductionOrder, boxNoBegin, boxCodeNum);

        // 箱码
        StringBuilder boxCodeStrBuilder = new StringBuilder();
        final List<SysCodeRuleDetail> boxCodeRuleDetailList = JSONUtil
                .toList(JSONUtil.toJsonStr(finishedCodeRule.getBoxCodeRuleList()), SysCodeRuleDetail.class);
        for (SysCodeRuleDetail m : boxCodeRuleDetailList) {
            String sourceField = m.getSourceField();
            BaseCodeFieldStrategy strategy = CODE_FIELD_STRATEGY_MAP.get(sourceField);
            if (strategy == null) {
                throw new BaseException("未注册 sourceField 策略: " + sourceField);
            }
            CodeFieldContext context = new CodeFieldContext();
            context.setFinishedProductionDate(finishedProductionDate);
            context.setFinishedProductionDepartCode(finishedProductionDepartCode);
            context.setFinishedProductionWorkshopCode(finishedProductionWorkshopCode);
            context.setFinishedOrderNo(finishedOrderNo);
            context.setFinishedProductCode(finishedProductCode);
            context.setSemiFinishedProductionDate(semiFinishedProductionDate);
            context.setSemiFinishedProductionDepartCode(semiFinishedProductionDepartCode);
            context.setSemiFinishedProductionWorkshopCode(semiFinishedProductionWorkshopCode);
            context.setSemiFinishedOrderNo(semiFinishedOrderNo);
            context.setSemiFinishedProductCode(semiFinishedProductCode);
            context.setFinishedTeamCode(finishedTeamCode);
            context.setSemiFinishedTeamCode(semiFinishedTeamCode);
            context.setSourceField(sourceField);
            context.setConstant(m.getConstant());
            context.setLength(m.getLength());
            context.setIndexBegin(m.getIndexBegin());
            context.setIndexEnd(m.getIndexEnd());
            context.setEncodeType(m.getEncodeType());
            context.setOffsetYears(m.getOffsetYears());
            context.setRandomType(m.getRandomType());
            String str = strategy.apply(context);
            boxCodeStrBuilder.append(str);
        }

        // 内箱码
        StringBuilder innerBoxCodeStrBuilder = new StringBuilder();
        final List<SysCodeRuleDetail> innerBoxCodeRuleDetailList = JSONUtil
                .toList(JSONUtil.toJsonStr(finishedCodeRule.getInnerBoxCodeRuleList()), SysCodeRuleDetail.class);
        for (SysCodeRuleDetail m : innerBoxCodeRuleDetailList) {
            String sourceField = m.getSourceField();
            BaseCodeFieldStrategy strategy = CODE_FIELD_STRATEGY_MAP.get(sourceField);
            if (strategy == null) {
                throw new BaseException("未注册 sourceField 策略: " + sourceField);
            }
            CodeFieldContext context = new CodeFieldContext();
            context.setFinishedProductionDate(finishedProductionDate);
            context.setFinishedProductionDepartCode(finishedProductionDepartCode);
            context.setFinishedProductionWorkshopCode(finishedProductionWorkshopCode);
            context.setFinishedOrderNo(finishedOrderNo);
            context.setFinishedProductCode(finishedProductCode);
            context.setSemiFinishedProductionDate(semiFinishedProductionDate);
            context.setSemiFinishedProductionDepartCode(semiFinishedProductionDepartCode);
            context.setSemiFinishedProductionWorkshopCode(semiFinishedProductionWorkshopCode);
            context.setSemiFinishedOrderNo(semiFinishedOrderNo);
            context.setSemiFinishedProductCode(semiFinishedProductCode);
            context.setFinishedTeamCode(finishedTeamCode);
            context.setSemiFinishedTeamCode(semiFinishedTeamCode);
            context.setSourceField(sourceField);
            context.setConstant(m.getConstant());
            context.setLength(m.getLength());
            context.setIndexBegin(m.getIndexBegin());
            context.setIndexEnd(m.getIndexEnd());
            context.setEncodeType(m.getEncodeType());
            context.setOffsetYears(m.getOffsetYears());
            context.setRandomType(m.getRandomType());
            String str = strategy.apply(context);
            innerBoxCodeStrBuilder.append(str);
        }

        // 半成品相关信息

        final ProductionDepartAndWorkshop semiFinishedProductionDepartAndWorkshop = productionDepartAndWorkshopMapper
                .getOneByCode(semiFinishedProductionWorkshopCode);
        final Long semiFinishedCodeRuleId = semiFinishedProductionDepartAndWorkshop.getCodeRuleId();
        SysCodeRule semiFinishedSysCodeRule = null;
        if (semiFinishedCodeRuleId != null) {
            semiFinishedSysCodeRule = sysCodeRuleMapper.selectById(semiFinishedCodeRuleId);

        } else {
            semiFinishedSysCodeRule = sysCodeRuleMapper.selectEnabled();
        }
        if (semiFinishedSysCodeRule == null) {
            throw new BaseException("编码规则不存在");
        }

        // 袋码
        final List<SysCodeRuleDetail> bagCodeRuleDetailList = JSONUtil
                .toList(JSONUtil.toJsonStr(semiFinishedSysCodeRule.getBagCodeRuleList()), SysCodeRuleDetail.class);
        StringBuilder bagCodeStrBuilder = new StringBuilder();
        for (SysCodeRuleDetail m : bagCodeRuleDetailList) {
            final String sourceField = m.getSourceField();
            BaseCodeFieldStrategy strategy = CODE_FIELD_STRATEGY_MAP.get(sourceField);
            if (strategy == null) {
                throw new BaseException("未注册 sourceField 策略: " + sourceField);
            }
            CodeFieldContext context = new CodeFieldContext();
            context.setFinishedProductionDate(finishedProductionDate);
            context.setFinishedProductionDepartCode(finishedProductionDepartCode);
            context.setFinishedProductionWorkshopCode(finishedProductionWorkshopCode);
            context.setFinishedOrderNo(finishedOrderNo);
            context.setFinishedProductCode(finishedProductCode);
            context.setSemiFinishedProductionDate(semiFinishedProductionDate);
            context.setSemiFinishedProductionDepartCode(semiFinishedProductionDepartCode);
            context.setSemiFinishedProductionWorkshopCode(semiFinishedProductionWorkshopCode);
            context.setSemiFinishedOrderNo(semiFinishedOrderNo);
            context.setSemiFinishedProductCode(semiFinishedProductCode);
            context.setFinishedTeamCode(finishedTeamCode);
            context.setSemiFinishedTeamCode(semiFinishedTeamCode);
            context.setSourceField(sourceField);
            context.setConstant(m.getConstant());
            context.setLength(m.getLength());
            context.setIndexBegin(m.getIndexBegin());
            context.setIndexEnd(m.getIndexEnd());
            context.setEncodeType(m.getEncodeType());
            context.setOffsetYears(m.getOffsetYears());
            context.setRandomType(m.getRandomType());
            String str = strategy.apply(context);
            bagCodeStrBuilder.append(str);

        }

        // 万用码
        final List<SysCodeRuleDetail> universalCodeRuleDetailList = JSONUtil
                .toList(JSONUtil.toJsonStr(finishedCodeRule.getUniversalCodeRuleList()), SysCodeRuleDetail.class);
        StringBuilder universalCodeStrBuilder = new StringBuilder();
        for (SysCodeRuleDetail m : universalCodeRuleDetailList) {
            final String sourceField = m.getSourceField();
            BaseCodeFieldStrategy strategy = CODE_FIELD_STRATEGY_MAP.get(sourceField);
            if (strategy == null) {
                throw new BaseException("未注册 sourceField 策略: " + sourceField);
            }
            CodeFieldContext context = new CodeFieldContext();
            context.setFinishedProductionDate(finishedProductionDate);
            context.setFinishedProductionDepartCode(finishedProductionDepartCode);
            context.setFinishedProductionWorkshopCode(finishedProductionWorkshopCode);
            context.setFinishedOrderNo(finishedOrderNo);
            context.setFinishedProductCode(finishedProductCode);
            context.setSemiFinishedProductionDate(semiFinishedProductionDate);
            context.setSemiFinishedProductionDepartCode(semiFinishedProductionDepartCode);
            context.setSemiFinishedProductionWorkshopCode(semiFinishedProductionWorkshopCode);
            context.setSemiFinishedOrderNo(semiFinishedOrderNo);
            context.setSemiFinishedProductCode(semiFinishedProductCode);
            context.setFinishedTeamCode(finishedTeamCode);
            context.setSemiFinishedTeamCode(semiFinishedTeamCode);
            context.setSourceField(sourceField);
            context.setConstant(m.getConstant());
            context.setLength(m.getLength());
            context.setIndexBegin(m.getIndexBegin());
            context.setIndexEnd(m.getIndexEnd());
            context.setEncodeType(m.getEncodeType());
            context.setOffsetYears(m.getOffsetYears());
            context.setRandomType(m.getRandomType());
            String str = strategy.apply(context);
            universalCodeStrBuilder.append(str);
        }

        int boxNoEnd = boxNoBegin + boxCodeNum;
        List<String> boxCodeList = new ArrayList<>();
        List<String> innerBoxCodeList = new ArrayList<>();
        List<String> bagCodeList = new ArrayList<>();

        String boxCodeTemplate = boxCodeStrBuilder.toString();
        String innerBoxCodeTemplate = innerBoxCodeStrBuilder.toString();
        String bagCodeTemplate = bagCodeStrBuilder.toString();
        String universalCodeTemplate = universalCodeStrBuilder.toString();
        log.info("万用码模板:{}", universalCodeTemplate);

        // 处理箱号{BOX_NO:数字}
        final List<String> boxNoTmpList = ReUtil.findAllGroup0(BOX_NO_PATTERN, boxCodeTemplate);
        int boxNoLength = 0;
        if (CollUtil.isNotEmpty(boxNoTmpList)) {
            Matcher matcher = BOX_NO_PATTERN.matcher(boxNoTmpList.get(0));
            if (matcher.find()) {
                boxNoLength = Integer.parseInt(matcher.group(1));
            }
        }

        final List<String> innerBoxNoTmpList = ReUtil.findAllGroup0(BOX_NO_PATTERN, innerBoxCodeTemplate);
        int innerBoxNoLength = 0;
        if (CollUtil.isNotEmpty(innerBoxNoTmpList)) {
            Matcher matcher = BOX_NO_PATTERN.matcher(innerBoxNoTmpList.get(0));
            if (matcher.find()) {
                innerBoxNoLength = Integer.parseInt(matcher.group(1));
            }
        }
        final List<String> bagNoTmpList = ReUtil.findAllGroup0(BOX_NO_PATTERN, bagCodeTemplate);
        int bagNoLength = 0;
        if (CollUtil.isNotEmpty(bagNoTmpList)) {
            Matcher matcher = BOX_NO_PATTERN.matcher(bagNoTmpList.get(0));
            if (matcher.find()) {
                bagNoLength = Integer.parseInt(matcher.group(1));
            }
        }

        // 处理随机字符串 {RANDOM_STRING:类型:数字}
        String boxCodeRandomType = null;
        int boxCodeRandomLength = 0;
        List<String> boxCodeRandomStringTmpList = ReUtil.findAllGroup0(RANDOM_STRING_GROUP_PATTERN, boxCodeTemplate);
        if (CollUtil.isNotEmpty(boxCodeRandomStringTmpList)) {
            Matcher matcher = RANDOM_STRING_GROUP_PATTERN.matcher(boxCodeRandomStringTmpList.get(0));
            if (matcher.find()) {
                boxCodeRandomType = matcher.group(1);
                boxCodeRandomLength = Integer.parseInt(matcher.group(2));
            }
        }

        String innerBoxCodeRandomType = null;
        int innerBoxCodeRandomLength = 0;
        List<String> innerBoxCodeRandomStringTmpList = ReUtil.findAllGroup0(RANDOM_STRING_GROUP_PATTERN,
                innerBoxCodeTemplate);
        if (CollUtil.isNotEmpty(innerBoxCodeRandomStringTmpList)) {
            Matcher matcher = RANDOM_STRING_GROUP_PATTERN.matcher(innerBoxCodeRandomStringTmpList.get(0));
            if (matcher.find()) {
                innerBoxCodeRandomType = matcher.group(1);
                innerBoxCodeRandomLength = Integer.parseInt(matcher.group(2));
            }
        }

        String bagCodeRandomType = null;
        int bagCodeRandomLength = 0;
        List<String> bagCodeRandomStringTmpList = ReUtil.findAllGroup0(RANDOM_STRING_GROUP_PATTERN, bagCodeTemplate);
        if (CollUtil.isNotEmpty(bagCodeRandomStringTmpList)) {
            Matcher matcher = RANDOM_STRING_GROUP_PATTERN.matcher(bagCodeRandomStringTmpList.get(0));
            if (matcher.find()) {
                bagCodeRandomType = matcher.group(1);
                bagCodeRandomLength = Integer.parseInt(matcher.group(2));
            }
        }

        String universalCodeRandomType = null;
        int universalCodeRandomLength = 0;
        List<String> universalCodeRandomStringTmpList = ReUtil.findAllGroup0(RANDOM_STRING_GROUP_PATTERN,
                universalCodeTemplate);
        if (CollUtil.isNotEmpty(universalCodeRandomStringTmpList)) {
            Matcher matcher = RANDOM_STRING_GROUP_PATTERN.matcher(universalCodeRandomStringTmpList.get(0));
            if (matcher.find()) {
                universalCodeRandomType = matcher.group(1);
                universalCodeRandomLength = Integer.parseInt(matcher.group(2));
            }
        }
        // 万用码做简单替换就行,不会有箱号的
        final String universalCodeRandomString = RandomStringUtil.generate(universalCodeRandomType,
                universalCodeRandomLength);
        String universalCode = ReUtil.replaceAll(universalCodeTemplate, RANDOM_STRING_PATTERN,
                universalCodeRandomString);
        log.info("万用码:{}", universalCode);

        String boxCodeTemplateTmp = "";
        String innerBoxCodeTemplateTmp = "";
        String bagCodeTemplateTmp = "";
        for (int currentBoxNo = boxNoBegin; currentBoxNo < boxNoEnd; currentBoxNo++) {
            // 箱
            boxCodeTemplateTmp = boxCodeTemplate;
            final String currentBoxNoStr = StrUtil.fillBefore(String.valueOf(currentBoxNo), '0', boxNoLength);
            boxCodeTemplateTmp = ReUtil.replaceAll(boxCodeTemplateTmp, BOX_NO_PATTERN, currentBoxNoStr);
            final String boxCodeRandomString = RandomStringUtil.generate(boxCodeRandomType, boxCodeRandomLength);
            boxCodeTemplateTmp = ReUtil.replaceAll(boxCodeTemplateTmp, RANDOM_STRING_PATTERN, boxCodeRandomString);
            boxCodeList.add(boxCodeTemplateTmp);
            log.info("箱号:{} ,随机字符串:{},箱码:{}", currentBoxNo, boxCodeRandomString, boxCodeTemplateTmp);
            // 内箱
            innerBoxCodeTemplateTmp = innerBoxCodeTemplate;
            final String currentInnerBoxNoStr = StrUtil.fillBefore(String.valueOf(currentBoxNo), '0', innerBoxNoLength);
            innerBoxCodeTemplateTmp = ReUtil.replaceAll(innerBoxCodeTemplateTmp, BOX_NO_PATTERN, currentInnerBoxNoStr);
            final String innerBoxCodeRandomString = RandomStringUtil.generate(innerBoxCodeRandomType,
                    innerBoxCodeRandomLength);
            innerBoxCodeTemplateTmp = ReUtil.replaceAll(innerBoxCodeTemplateTmp, RANDOM_STRING_PATTERN,
                    innerBoxCodeRandomString);
            innerBoxCodeList.add(innerBoxCodeTemplateTmp);
            log.info("箱号:{} ,随机字符串:{},内箱码:{}", currentBoxNo, innerBoxCodeRandomString, innerBoxCodeTemplateTmp);
            // 袋
            bagCodeTemplateTmp = bagCodeTemplate;
            final String currentBagNoStr = StrUtil.fillBefore(String.valueOf(currentBoxNo), '0', bagNoLength);
            bagCodeTemplateTmp = ReUtil.replaceAll(bagCodeTemplateTmp, BOX_NO_PATTERN, currentBagNoStr);
            final String bagCodeRandomString = RandomStringUtil.generate(bagCodeRandomType, bagCodeRandomLength);
            bagCodeTemplateTmp = ReUtil.replaceAll(bagCodeTemplateTmp, RANDOM_STRING_PATTERN, bagCodeRandomString);
            bagCodeList.add(bagCodeTemplateTmp);
            log.info("箱号:{} ,随机字符串:{},袋码:{}", currentBoxNo, bagCodeRandomString, bagCodeTemplateTmp);
        }
//        int a = 1 / 0;

        // 写入箱码
        final LocalDateTime now = LocalDateTimeUtil.now();
        final PullCodeVO pullCodeVO = new PullCodeVO();
        pullCodeVO.setUniversalCode(universalCode);
        if (StrUtil.equals(PullCodeEnums.QR_CODE.getCode(), type)) {
            // 二维码
            // 按照 数量生成二维码
            final List<String> qrCodeSet = qrCodeCache.getQrCode(qrCodeNum);
            if (CollUtil.size(qrCodeSet) != qrCodeNum) {
                throw new BaseException("二维码拉取失败,请联系管理员!");
            }
            final List<String> qrCodeList = qrCodeSet.stream().map(m -> qrCodeUrlPrefix + m).toList();

            final PullCodeVO.QrCodeTypeData qrCodeTypeData = new PullCodeVO.QrCodeTypeData();
            pullCodeVO.setQrCodeTypeData(qrCodeTypeData);
            qrCodeTypeData.setQrCodeList(qrCodeList);
            List<PullCodeVO.BoxCodeData> boxCodeDataList = new ArrayList<>();
            for (int i = 0; i < boxCodeList.size(); i++) {
                final PullCodeVO.BoxCodeData boxCodeData = new PullCodeVO.BoxCodeData();
                final String boxCode = boxCodeList.get(i);
                final String innerBoxCode = innerBoxCodeList.get(i);
                boxCodeData.setBoxCode(boxCode);
                boxCodeData.setInnerBoxCode(innerBoxCode);
                boxCodeDataList.add(boxCodeData);
            }

            qrCodeTypeData.setBoxCodeDataList(boxCodeDataList);

            final List<RecordBoxCode> recordBoxCodeList = boxCodeList.stream().map(m -> {
                final RecordBoxCode recordBoxCode = new RecordBoxCode();
                recordBoxCode.setFinishedProductOrderId(finishedProductOrderId);
                recordBoxCode.setSemiFinishedProductOrderId(semiFinishedProductOrderId);
                recordBoxCode.setCode(m);
                recordBoxCode.setPullType(PullCodeEnums.QR_CODE.getCode());
                recordBoxCode.setRuleId(ruleId);
                recordBoxCode.setPullDateTime(now);
                return recordBoxCode;
            }).toList();
            recordBoxCodeService.saveBatch(recordBoxCodeList);
            final List<RecordQrCode> recordQrCodeList = qrCodeSet.stream().map(m -> {
                final RecordQrCode recordQrCode = new RecordQrCode();
                recordQrCode.setCode(m);
                recordQrCode.setPullDateTime(now);
                recordQrCode.setFinishedProductOrderId(finishedProductOrderId);
                recordQrCode.setSemiFinishedProductOrderId(semiFinishedProductOrderId);
                return recordQrCode;
            }).toList();
            recordQrCodeService.saveBatch(recordQrCodeList);

        }
        if (StrUtil.equals(PullCodeEnums.LOGISTICS_CODE.getCode(), type)) {
            List<PullCodeVO.LogisticsTypeData> logisticsTypeDataList = new ArrayList<>();
            for (int i = 0; i < boxCodeList.size(); i++) {
                final PullCodeVO.LogisticsTypeData logisticsTypeData = new PullCodeVO.LogisticsTypeData();
                final String boxCode = boxCodeList.get(i);
                final String innerBoxCode = innerBoxCodeList.get(i);
                final String bagCode = bagCodeList.get(i);
                logisticsTypeData.setBoxCode(boxCode);
                logisticsTypeData.setBagCode(bagCode);
                logisticsTypeData.setInnerBoxCode(innerBoxCode);
                logisticsTypeDataList.add(logisticsTypeData);
            }
            pullCodeVO.setLogisticsTypeDataList(logisticsTypeDataList);
            final List<RecordBoxCode> recordBoxCodeList = new ArrayList<>();
            final List<RecordBagCode> recordBagCodeList = new ArrayList<>();

            logisticsTypeDataList.forEach(logisticsTypeData -> {
                final String boxCode = logisticsTypeData.getBoxCode();
                final String bagCode = logisticsTypeData.getBagCode();
                final RecordBoxCode recordBoxCode = new RecordBoxCode();
                recordBoxCode.setFinishedProductOrderId(finishedProductOrderId);
                recordBoxCode.setSemiFinishedProductOrderId(semiFinishedProductOrderId);
                recordBoxCode.setCode(boxCode);
                recordBoxCode.setPullType(PullCodeEnums.LOGISTICS_CODE.getCode());
                recordBoxCode.setRuleId(ruleId);
                recordBoxCode.setPullDateTime(now);
                recordBoxCodeList.add(recordBoxCode);
                RecordBagCode recordBagCode = new RecordBagCode();
                recordBagCode.setCode(bagCode);
                recordBagCode.setSemiFinishedProductOrderId(semiFinishedProductOrderId);
                recordBagCode.setFinishedProductOrderId(finishedProductOrderId);
                recordBagCode.setBoxCode(boxCode);
                recordBagCode.setRuleId(ruleId);
                recordBagCode.setPullDateTime(now);
                recordBagCodeList.add(recordBagCode);

            });
            recordBoxCodeService.saveBatch(recordBoxCodeList);
            recordBagCodeService.saveBatch(recordBagCodeList);
        }

        return pullCodeVO;
    }

    /**
     * 采集上传
     *
     * @param recordCodeUploadDTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void collectUpload(RecordCodeUploadDTO recordCodeUploadDTO) {
        final LocalDateTime now = LocalDateTimeUtil.now();
        // 二维码上传
        final RecordCodeUploadDTO.RecordQrCodeUploadDTO qrCodeUploadDTO = recordCodeUploadDTO.getQrCodeUploadDTO();
        final String boxCode = qrCodeUploadDTO.getBoxCode();
        final List<String> qrCodeList = qrCodeUploadDTO.getQrCodeList();
        if (StrUtil.isNotBlank(boxCode) && CollUtil.isNotEmpty(qrCodeList)) {
            final RecordBoxCode recordBoxCode = recordBoxCodeService.getOneByBoxCode(boxCode);
            if (recordBoxCode == null) {
                throw new BaseException("箱码:{}不存在", boxCode);
            }
            recordBoxCode.setUploadDateTime(now);
            recordBoxCodeService.updateById(recordBoxCode);
            List<RecordQrCode> recordQrCodeList = recordQrCodeService.listByQrCode(qrCodeList);
            recordQrCodeList.forEach(n -> {
                n.setUploadDateTime(now);
                n.setBoxCode(boxCode);
            });
            recordQrCodeService.updateBatchById(recordQrCodeList);

        }

        final RecordCodeUploadDTO.CribCodeUploadDTO cribCodeUploadDTO = recordCodeUploadDTO.getCribCodeUploadDTO();
        final String cribCode = cribCodeUploadDTO.getCribCode();
        final List<String> boxCodeList = cribCodeUploadDTO.getBoxCodeList();
        if (StrUtil.isNotBlank(cribCode) && CollUtil.isNotEmpty(boxCodeList)) {
            List<RecordBoxCode> recordBoxCodeList = recordBoxCodeService.listByBoxCode(boxCodeList);
            recordBoxCodeList.forEach(n -> {
                n.setCribCode(cribCode);
                final LocalDateTime uploadDateTime = n.getUploadDateTime();
                if (uploadDateTime == null) {
                    n.setUploadDateTime(now);
                }
                n.setCribDateTime(now);
            });

            recordBoxCodeService.updateBatchById(recordBoxCodeList);

            boxCodeList.forEach(m -> {
                final RecordBoxCode subRecordBoxCode = recordBoxCodeService.getOneByBoxCode(m);
                if (subRecordBoxCode == null) {
                    throw new BaseException("箱码:{}不存在", m);
                }
                final String pullType = subRecordBoxCode.getPullType();
                if (StrUtil.equals(pullType, "LOGISTICS_CODE")) {
                    List<RecordBagCode> recordBagCodeList = recordBagCodeService.listByBoxCode(m);
                    recordBagCodeList.forEach(n -> {
                        n.setUploadDateTime(now);
                    });
                    recordBagCodeService.updateBatchById(recordBagCodeList);
                }

            });
        }
    }

    /**
     * erp订单同步
     *
     * @param erpOrderDTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncOrder(ErpOrderDTO erpOrderDTO) {
        final String orderNo = StrUtil.emptyIfNull(erpOrderDTO.getOrderNo());
        final String orderDateStr = erpOrderDTO.getOrderDate();
        // 2025-05-29 00:00:00.0
        // 截取前10位 2025-05-29 转LocalDate
        final LocalDate orderDate = LocalDateTimeUtil.parseDate(orderDateStr.substring(0, 10),
                DatePattern.NORM_DATE_PATTERN);
        final String productionDateStr = StrUtil.emptyIfNull(erpOrderDTO.getProductionDate());
        final LocalDate productionDate = LocalDateTimeUtil.parseDate(productionDateStr.substring(0, 10),
                DatePattern.NORM_DATE_PATTERN);
        final String productionDepartCode = StrUtil.emptyIfNull(erpOrderDTO.getProductionDepartCode());
        final String productionWorkshopCode = StrUtil.emptyIfNull(erpOrderDTO.getProductionWorkshopCode());
        final String productionShiftCode = StrUtil.emptyIfNull(erpOrderDTO.getProductionShiftCode());
        final String productionTeamCode = StrUtil.emptyIfNull(erpOrderDTO.getProductionTeamCode());
        final String productCode = StrUtil.emptyIfNull(erpOrderDTO.getProductCode());
        final String boxNumStr = StrUtil.emptyIfNull(erpOrderDTO.getBoxNum());
        Integer boxNum = null;
        if (StrUtil.isNotBlank(boxNumStr)) {
            boxNum = new BigDecimal(boxNumStr).toBigInteger().intValue();
        }
        final String bagNumStr = StrUtil.emptyIfNull(erpOrderDTO.getBagNum());
        Integer bagNum = null;
        if (StrUtil.isNotBlank(bagNumStr)) {
            bagNum = new BigDecimal(bagNumStr).toBigInteger().intValue();
        }
        final String boxNumMaxLimitedStr = StrUtil.emptyIfNull(erpOrderDTO.getBoxNumMaxLimited());
        Integer boxNumMaxLimited = null;
        if (StrUtil.isNotBlank(boxNumMaxLimitedStr)) {
            boxNumMaxLimited = new BigDecimal(boxNumMaxLimitedStr).toBigInteger().intValue();
        }
        final String bagNumMaxLimitedStr = StrUtil.emptyIfNull(erpOrderDTO.getBagNumMaxLimited());
        Integer bagNumMaxLimited = null;
        if (StrUtil.isNotBlank(bagNumMaxLimitedStr)) {
            bagNumMaxLimited = new BigDecimal(bagNumMaxLimitedStr).toBigInteger().intValue();
        }
        final ProductionProduct productionProduct = productionProductMapper.getOneByCode(productCode);
        if (productionProduct == null) {
            throw new BaseException("产品编码:{}不存在", productCode);
        }
        final String productType = productionProduct.getProductType();
        // 先处理班组,班次
        productionShiftMapper.syncOne(productionShiftCode);
        productionTeamMapper.syncOne(productionTeamCode);
        ProductionOrder productionOrderDB = baseMapper.getOneByOrderNo(orderNo);
        if (productionOrderDB == null) {
            final ProductionOrder productionOrder = new ProductionOrder();
            productionOrder.setOrderNo(orderNo);
            productionOrder.setProductionShiftCode(productionShiftCode);
            productionOrder.setProductionTeamCode(productionTeamCode);
            productionOrder.setProductionDepartCode(productionDepartCode);
            productionOrder.setProductionWorkshopCode(productionWorkshopCode);
            productionOrder.setOrderDate(orderDate);
            productionOrder.setProductType(productType);
            productionOrder.setProductionDate(productionDate);
            productionOrder.setProductCode(productCode);
            productionOrder.setBoxNum(boxNum);
            productionOrder.setBagNum(bagNum);
            productionOrder.setBoxNumMaxLimited(boxNumMaxLimited);
            productionOrder.setBagNumMaxLimited(bagNumMaxLimited);
            save(productionOrder);

        } else {
            // 除了订单号,全部修改
            productionOrderDB.setProductionShiftCode(productionShiftCode);
            productionOrderDB.setProductionTeamCode(productionTeamCode);
            productionOrderDB.setProductionDepartCode(productionDepartCode);
            productionOrderDB.setProductionWorkshopCode(productionWorkshopCode);
            productionOrderDB.setOrderDate(orderDate);
            productionOrderDB.setProductType(productType);
            productionOrderDB.setProductionDate(productionDate);
            productionOrderDB.setProductCode(productCode);
            productionOrderDB.setBoxNum(boxNum);
            productionOrderDB.setBagNum(bagNum);
            productionOrderDB.setBoxNumMaxLimited(boxNumMaxLimited);
            productionOrderDB.setBagNumMaxLimited(bagNumMaxLimited);
            updateById(productionOrderDB);

        }

    }

    /**
     * 填充数据
     *
     * @param m
     */

    private void accept(ProductionOrderVO m) {
        final LocalDate productionDate = m.getProductionDate();
        if (productionDate != null) {
            final String productionDateStr = LocalDateTimeUtil.format(productionDate, DatePattern.PURE_DATE_PATTERN);
            m.setProductionBatchNo(
                    EncodeConvertUtils.convert(Integer.parseInt(productionDateStr), BaseEncodeEnums.BASE_62.getCode()));
            final LocalDate limitedUseDate = productionDate.plusYears(3);
            m.setLimitedUseDateStr(LocalDateTimeUtil.format(limitedUseDate, DatePattern.PURE_DATE_PATTERN));
        }
        final String productType = m.getProductType();
        final Long id = m.getId();
        if (StrUtil.equals(ProductTypeEnum.FINISHED_PRODUCT.getCode(), productType)) {
            m.setBoxCodeCount(recordBoxCodeService.getCountByFinishedProductionOrderId(id));

        }
        if (StrUtil.equals(ProductTypeEnum.SEMI_FINISHED_PRODUCT.getCode(), productType)) {
            m.setBoxCodeCount(recordBoxCodeService.getCountBySemiFinishedProductionOrderId(id));
        }
        final String productionWorkshopCode = m.getProductionWorkshopCode();
        final ProductionDepartAndWorkshop productionDepartAndWorkshop = productionDepartAndWorkshopMapper
                .getOneByCode(productionWorkshopCode);

        final String productionTeamCode = m.getProductionTeamCode();
        // 取第一个字符
        final String sub = StrUtil.sub(productionTeamCode, 0, 1);
        String alias = "";
        if (productionDepartAndWorkshop != null) {
            alias = productionDepartAndWorkshop.getAlias();
        }
        m.setPrintCode(sub + alias);

    }

}