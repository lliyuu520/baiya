package com.miguoma.by.modules.production.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.impl.BaseServiceImpl;
import com.miguoma.by.common.cache.QrCodeCache;
import com.miguoma.by.common.exception.BaseException;
import com.miguoma.by.common.utils.encode.WebBase62;
import com.miguoma.by.modules.client.dto.PullCodeDTO;
import com.miguoma.by.modules.client.dto.RecordCodeUploadDTO;
import com.miguoma.by.modules.client.vo.PullCodeVO;
import com.miguoma.by.modules.erp.dto.ErpOrderDTO;
import com.miguoma.by.modules.production.convert.ProductionOrderConvert;
import com.miguoma.by.modules.production.dto.ProductionOrderDTO;
import com.miguoma.by.modules.production.entity.ProductionOrder;
import com.miguoma.by.modules.production.entity.ProductionProduct;
import com.miguoma.by.modules.production.enums.ProductTypeEnum;
import com.miguoma.by.modules.production.mapper.ProductionOrderMapper;
import com.miguoma.by.modules.production.mapper.ProductionProductMapper;
import com.miguoma.by.modules.production.mapper.ProductionShiftMapper;
import com.miguoma.by.modules.production.mapper.ProductionTeamMapper;
import com.miguoma.by.modules.production.query.ProductionOrderQuery;
import com.miguoma.by.modules.production.service.ProductionOrderService;
import com.miguoma.by.modules.production.vo.ProductionOrderVO;
import com.miguoma.by.modules.record.entity.RecordBagCode;
import com.miguoma.by.modules.record.entity.RecordBoxCode;
import com.miguoma.by.modules.record.entity.RecordQrCode;
import com.miguoma.by.modules.record.service.RecordBagCodeService;
import com.miguoma.by.modules.record.service.RecordBoxCodeService;
import com.miguoma.by.modules.record.service.RecordQrCodeService;
import com.miguoma.by.modules.system.convert.SysCodeRuleDetailConvert;
import com.miguoma.by.modules.system.entity.SysCodeRule;
import com.miguoma.by.modules.system.entity.SysCodeRuleDetail;
import com.miguoma.by.modules.system.enums.EncodeTypeEnums;
import com.miguoma.by.modules.system.enums.RuleTypeEnums;
import com.miguoma.by.modules.system.enums.SourceFiledEnums;
import com.miguoma.by.modules.system.mapper.SysCodeRuleDetailMapper;
import com.miguoma.by.modules.system.mapper.SysCodeRuleMapper;
import com.miguoma.by.modules.system.vo.SysCodeRuleDetailVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 生产订单服务实现类
 *
 * @author liliangyu
 */
@Service
@RequiredArgsConstructor
public class ProductionOrderServiceImpl extends BaseServiceImpl<ProductionOrderMapper, ProductionOrder>
    implements ProductionOrderService {

    private final SysCodeRuleMapper sysCodeRuleMapper;
    private final SysCodeRuleDetailMapper sysCodeRuleDetailMapper;
    private final QrCodeCache qrCodeCache;

    private final ProductionProductMapper productionProductMapper;
    private final RecordBoxCodeService recordBoxCodeService;
    private final RecordQrCodeService recordQrCodeService;
    private final RecordBagCodeService recordBagCodeService;
    private final ProductionShiftMapper productionShiftMapper;
    private final ProductionTeamMapper productionTeamMapper;

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
            if (StrUtil.equals(ProductTypeEnum.FINISHED_PRODUCT.getCode(),productType)) {
                m.setBoxCodeCount(recordBoxCodeService.getCountByFinishedProductionOrderId(id));
                m.setBagCodeCount(recordBagCodeService.getCountByFinishedProductionOrderId(id));
                m.setQrCodeCount(recordQrCodeService.getCountByFinishedProductionOrderId(id));
            }
            if (StrUtil.equals(ProductTypeEnum.SEMI_FINISHED_PRODUCT.getCode(),productType)) {
                m.setBoxCodeCount(recordBoxCodeService.getCountBySemiFinishedProductionOrderId(id));
                m.setBagCodeCount(recordBagCodeService.getCountBySemiFinishedProductionOrderId(id));
                m.setQrCodeCount(recordQrCodeService.getCountBySemiFinishedProductionOrderId(id));
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
        records.forEach(m -> {
            final String productCode = m.getProductCode();
            final ProductionProduct productionProduct = productionProductMapper.getOneByCode(productCode);
            if (productionProduct != null) {
                final LocalDate productionDate = m.getProductionDate();
                if (productionDate != null) {
                    final String productionDateStr = LocalDateTimeUtil.format(productionDate, "yyyyMMdd");
                    m.setProductionBatchNo(WebBase62.encode(Long.parseLong(productionDateStr)));
                    final LocalDate limitedUseDate = productionDate.plusYears(3);
                    m.setLimitedUseDateStr(LocalDateTimeUtil.format(limitedUseDate, "yyyyMMdd"));
                }
                final String productType = m.getProductType();
                final Long id = m.getId();
                if (StrUtil.equals(ProductTypeEnum.FINISHED_PRODUCT.getCode(),productType)) {
                    m.setBoxCodeCount(recordBoxCodeService.getCountByFinishedProductionOrderId(id));
                    m.setBagCodeCount(recordBagCodeService.getCountByFinishedProductionOrderId(id));
                    m.setQrCodeCount(recordQrCodeService.getCountByFinishedProductionOrderId(id));

                }
                if (StrUtil.equals(ProductTypeEnum.SEMI_FINISHED_PRODUCT.getCode(),productType)) {
                    m.setBoxCodeCount(recordBoxCodeService.getCountBySemiFinishedProductionOrderId(id));
                    m.setBagCodeCount(recordBagCodeService.getCountBySemiFinishedProductionOrderId(id));
                    m.setQrCodeCount(recordQrCodeService.getCountBySemiFinishedProductionOrderId(id));
                }


            }

        });
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
    public PullCodeVO pullCode(PullCodeDTO pullCodeDTO) {
        final Long finishedProductOrderId = pullCodeDTO.getFinishedProductOrderId();
        final Long semiFinishedProductOrderId = pullCodeDTO.getSemiFinishedProductOrderId();
        final ProductionOrder productionOrder = getById(finishedProductOrderId);
        if (productionOrder == null) {
            throw new BaseException("订单不存在");
        }

        final SysCodeRule sysCodeRule = sysCodeRuleMapper.selectEnabled();
        if (sysCodeRule == null) {
            throw new BaseException("当前没有生效的生码规则");
        }

        final String productCode = productionOrder.getProductCode();
        final ProductionProduct productionProduct = productionProductMapper.getOneByCode(productCode);
        if (productionProduct == null) {
            throw new BaseException("产品不存在");
        }
        final Integer oneBoxPackageNum = productionProduct.getOneBoxPackageNum();

        final LocalDate orderDate = productionOrder.getOrderDate();
        final LocalDate productionDate = productionOrder.getProductionDate();
        final String productionDepartCode = productionOrder.getProductionDepartCode();
        final String productionWorkshopCode = productionOrder.getProductionWorkshopCode();
        // 班组和班次都是中文
        final String productionShiftCode = productionOrder.getProductionShiftCode();
        final String productionTeamCode = productionOrder.getProductionTeamCode();

        final Integer boxNoBegin = pullCodeDTO.getBoxNoBegin();
        final Integer boxCodeNum = pullCodeDTO.getBoxCodeNum();
        final Integer bagCodeNum = oneBoxPackageNum * boxCodeNum;
        final Integer qrCodeNum = oneBoxPackageNum * boxCodeNum;
        final String type = pullCodeDTO.getType();
        final Long ruleId = sysCodeRule.getId();
        refreshBoxNo(productionOrder, boxNoBegin, boxCodeNum);

        // 物流码
        final List<SysCodeRuleDetail> sysCodeRuleDetails = sysCodeRuleDetailMapper.selectListByRuleIdSAndType(ruleId,
            RuleTypeEnums.BOX.getCode());
        final List<SysCodeRuleDetailVO> sysCodeRuleDetailVOS = SysCodeRuleDetailConvert.INSTANCE
            .convertList(sysCodeRuleDetails);
        StringBuilder code = new StringBuilder();
        for (SysCodeRuleDetailVO m : sysCodeRuleDetailVOS) {
            final String sourceField = m.getSourceField();
            final String constant = m.getConstant();
            final Integer indexBegin = m.getIndexBegin();
            final Integer indexEnd = m.getIndexEnd();
            final String encodeType = m.getEncodeType();

String str="";
                //限用日期
                if (StrUtil.equals(sourceField, SourceFiledEnums.LIMITED_USE_DATE.getCode())) {
                    final LocalDate limitedUseDate = productionDate.plusYears(3);
                    str=LocalDateTimeUtil.format(limitedUseDate, "yyyyMMdd");

                }
                // 部门编码
                if (StrUtil.equals(sourceField, SourceFiledEnums.PRODUCTION_DEPART_CODE.getCode())) {
                    str=productionDepartCode;
                }
                // 车间编码
                if (StrUtil.equals(sourceField, SourceFiledEnums.PRODUCTION_WORKSHOP_CODE.getCode())) {
                    str = productionWorkshopCode;
                }
                // 订单编码
                if (StrUtil.equals(sourceField, SourceFiledEnums.ORDER_CODE.getCode())) {
                    str = productionOrder.getOrderNo();
                }
                // 产品编码
                if (StrUtil.equals(sourceField, SourceFiledEnums.PRODUCT_CODE.getCode())) {
                    str = productCode;
                }
                // 箱号 需要填充 使用占位符即可
                if (StrUtil.equals(sourceField, SourceFiledEnums.BOX_NO.getCode())) {
                    str = "{}";

                }
                // 常量
                if (StrUtil.equals(sourceField, SourceFiledEnums.CONSTANT.getCode())) {
                    str=constant;
                }
                // 开始位 结束位
            // 非箱号和常量 需要截取,修改编码方式
            if ((!StrUtil.equals(sourceField, SourceFiledEnums.ORDER_CODE.getCode())&&(!StrUtil.equals(sourceField, SourceFiledEnums.CONSTANT.getCode())))) {
                str=StrUtil.sub(str, indexBegin, indexEnd);
                // 修改编码方式
                if (StrUtil.equals(encodeType, EncodeTypeEnums.BASE_62.getCode())) {
                    str = WebBase62.encode(Long.parseLong(str));
                }
//                Base62.encode().
            //    if(StrUtil.equals(encodeType,EncodeTypeEnums.BASE_36.getCode())){
            //     str=StrUtil.upperCase(str);
            //    }
            //    if(StrUtil.equals(encodeType,EncodeTypeEnums.BASE_32.getCode())){
            //     str=StrUtil.upperCase(str);
            //    }
            //    if(StrUtil.equals(encodeType,EncodeTypeEnums.BASE_16.getCode())){




            }
            // 编码





              




        }
        int boxNoEnd = boxNoBegin + boxCodeNum;
        List<String> boxCodeList = new ArrayList<>();
        for (int i = boxNoBegin; i < boxNoEnd; i++) {
            String boxNo = StrUtil.fillBefore(String.valueOf(i), '0', 4);
            final String s = RandomUtil.randomString(8);
            final String s1 = s + boxNo;
            boxCodeList.add(StrUtil.format(code, s1));
        }
        // 写入箱码

        final LocalDateTime now = LocalDateTimeUtil.now();
        final String format = LocalDateTimeUtil.format(now, "yyyyMMdd");
        final String encode = WebBase62.encode(Long.parseLong(format));
        final PullCodeVO pullCodeVO = new PullCodeVO();
        if (StrUtil.equals("QR_CODE", type)) {
            // 二维码
            // 按照 数量生成二维码
            final List<String> qrCodeSet = qrCodeCache.getQrCode(qrCodeNum);
            final List<String> list = qrCodeSet.stream().map(m -> "http://b.bygf.top/" + encode + m).toList();

            final PullCodeVO.QrCodeTypeData qrCodeTypeData = new PullCodeVO.QrCodeTypeData();
            pullCodeVO.setQrCodeTypeData(qrCodeTypeData);
            qrCodeTypeData.setQrCodeList(list);
            qrCodeTypeData.setBoxCodeList(boxCodeList);

            final List<RecordBoxCode> recordBoxCodeList = boxCodeList.stream().map(m -> {
                final RecordBoxCode recordBoxCode = new RecordBoxCode();
                recordBoxCode.setFinishedProductOrderId(finishedProductOrderId);
                recordBoxCode.setSemiFinishedProductOrderId(semiFinishedProductOrderId);
                recordBoxCode.setCode(m);
                recordBoxCode.setPullType("QR_CODE");
                recordBoxCode.setRuleId(ruleId);
                recordBoxCode.setPullDateTime(now);
                return recordBoxCode;
            }).toList();
            recordBoxCodeService.saveBatch(recordBoxCodeList);
            final List<RecordQrCode> recordQrCodeList = qrCodeSet.stream().map(m -> {
                final RecordQrCode recordQrCode = new RecordQrCode();
                recordQrCode.setCode(encode + m);
                recordQrCode.setPullDateTime(now);
                recordQrCode.setFinishedProductOrderId(finishedProductOrderId);
                recordQrCode.setSemiFinishedProductOrderId(semiFinishedProductOrderId);
                return recordQrCode;
            }).toList();
            recordQrCodeService.saveBatch(recordQrCodeList);

        }
        if (StrUtil.equals("BOX_CODE", type)) {

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

        final List<RecordCodeUploadDTO.RecordQrCodeUploadDTO> qrCodeUploadDTOList = recordCodeUploadDTO
            .getQrCodeUploadDTOList();
        qrCodeUploadDTOList.forEach(m -> {
            final String boxCode = m.getBoxCode();
            final List<String> qrCodeList = m.getQrCodeList();
            final RecordBoxCode recordBoxCode = recordBoxCodeService.getOneByBoxCode(boxCode);
            if (recordBoxCode == null) {
                throw new BaseException("箱码:{}不存在", boxCode);
            }
            recordBoxCode.setUploadDateTime(now);
            recordBoxCodeService.updateById(recordBoxCode);
            List<RecordQrCode> recordQrCodeList = recordQrCodeService.listByQrCode(qrCodeList);
            recordQrCodeList.forEach(n -> {
                n.setUploadDateTime(now);
            });
            recordQrCodeService.updateBatchById(recordQrCodeList);
        });
        final List<String> boxCodeList = recordCodeUploadDTO.getBoxCodeList();
        boxCodeList.forEach(m -> {
            final RecordBoxCode recordBoxCode = recordBoxCodeService.getOneByBoxCode(m);
            if (recordBoxCode == null) {
                throw new BaseException("箱码:{}不存在", m);
            }
            recordBoxCode.setUploadDateTime(now);
            recordBoxCodeService.updateById(recordBoxCode);
            // 这种方式需要把袋码找出来一并处理了
            List<RecordBagCode> recordBagCodeList = recordBagCodeService.listByBoxCode(m);
            recordBagCodeList.forEach(n -> {
                n.setUploadDateTime(now);
            });
            recordBagCodeService.updateBatchById(recordBagCodeList);
        });
        final List<RecordCodeUploadDTO.CribCodeUploadDTO> cribCodeUploadDTOList = recordCodeUploadDTO
            .getCribCodeUploadDTOList();
        cribCodeUploadDTOList.forEach(m -> {
            final String cribCode = m.getCribCode();
            final List<String> boxCodeList1 = m.getBoxCodeList();
            List<RecordBoxCode> recordBoxCodeList = recordBoxCodeService.listByBoxCode(boxCodeList1);
            recordBoxCodeList.forEach(n -> {
                n.setCribCode(cribCode);
                n.setCribDateTime(now);
            });

            recordBoxCodeService.updateBatchById(recordBoxCodeList);
        });

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
        final LocalDate orderDate = LocalDateTimeUtil.parseDate(orderDateStr.substring(0, 10), "yyyy-MM-dd");
        final String productionDateStr = StrUtil.emptyIfNull(erpOrderDTO.getProductionDate());
        final LocalDate productionDate = LocalDateTimeUtil.parseDate(productionDateStr.substring(0, 10), "yyyy-MM-dd");
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
        if(productionProduct == null){
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
}