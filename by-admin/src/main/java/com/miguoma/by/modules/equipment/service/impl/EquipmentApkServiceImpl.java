package com.miguoma.by.modules.equipment.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.base.service.impl.BaseServiceImpl;
import com.miguoma.by.common.exception.BaseException;
import com.miguoma.by.modules.equipment.convert.EquipmentApkConvert;
import com.miguoma.by.modules.equipment.dto.EquipmentApkDTO;
import com.miguoma.by.modules.equipment.entity.EquipmentApk;
import com.miguoma.by.modules.equipment.mapper.EquipmentApkMapper;
import com.miguoma.by.modules.equipment.query.EquipmentApkQuery;
import com.miguoma.by.modules.equipment.service.EquipmentApkService;
import com.miguoma.by.modules.equipment.vo.EquipmentApkVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * APK管理服务实现类
 * 实现APK版本管理的核心业务逻辑，包括版本发布、查询、更新等功能
 *
 * @author liliangyu
 */
@Service
@RequiredArgsConstructor
public class EquipmentApkServiceImpl extends BaseServiceImpl<EquipmentApkMapper, EquipmentApk> implements EquipmentApkService {

    @Value("${apk.path}")
    private String apkPath;

    @Value("${apk.location}")
    private String apkLocation;

    /**
     * 分页查询APK版本列表
     * 支持按版本号、版本名称等条件进行查询
     *
     * @param query 查询条件，包含版本号、版本名称等
     * @return 分页结果，包含APK版本信息列表
     */
    @Override
    public PageVO<EquipmentApkVO> pageVO(EquipmentApkQuery query) {
        IPage<EquipmentApk> page = page(getPage(query), builderWrapper(query));
        return PageVO.of(EquipmentApkConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    /**
     * 新增APK版本
     * 用于发布新的APK版本
     *
     * @param dto APK版本信息，包含版本号、下载地址、版本描述等
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOne(EquipmentApkDTO dto) {
        EquipmentApk equipmentApk = EquipmentApkConvert.INSTANCE.convertFromDTO(dto);
        save(equipmentApk);
    }

    /**
     * 编辑APK版本
     * 用于修改已发布的APK版本信息
     *
     * @param dto APK版本信息，包含版本号、下载地址、版本描述等
     */
    @Override
    public void updateOne(EquipmentApkDTO dto) {
        EquipmentApk equipmentApk = EquipmentApkConvert.INSTANCE.convertFromDTO(dto);
        updateById(equipmentApk);
    }

    /**
     * 获取APK版本详情
     * 用于查看指定APK版本的详细信息
     *
     * @param id APK版本ID
     * @return APK版本详细信息
     */
    @Override
    public EquipmentApkVO getOneById(Long id) {
        EquipmentApk equipmentApk = getById(id);
        return EquipmentApkConvert.INSTANCE.convertToVO(equipmentApk);
    }

    /**
     * 删除APK版本
     * 用于删除指定的APK版本记录
     *
     * @param id APK版本ID
     */
    @Override
    public void deleteById(Long id) {
        removeById(id);
    }

    /**
     * 获取最新APK版本
     * 用于获取当前最新的APK版本信息
     *
     * @return 最新APK版本信息
     */
    @Override
    public EquipmentApkVO getLatest() {
        return baseMapper.getLatest();
    }

    /**
     * 查询最新的APK版本号
     *
     * @return
     */
    @Override
    public Long getLatestVersionNo() {
        return baseMapper.getLatestVersionNo();
    }

    /**
     * 上传APK文件
     * 用于上传新的APK文件
     *
     * @param file APK文件
     * @return 上传结果信息
     */
    @Override
    public String uploadApk(MultipartFile file) {
        // /home/blmtest/apk 文件存放在这里,已经使用nginx 做文件服务映射了
        String originalFilename = file.getOriginalFilename();
        final List<String> split = StrUtil.split(originalFilename, StrUtil.DOT);
        if (split.size() != 2) {
            throw new BaseException("文件格式错误");
        }
        String fileName = split.get(0);
        String fileTypeName = split.get(1);

        // 需要重命名才行,不然文件会覆盖
        String newFilename = fileName + "_" + IdUtil.fastSimpleUUID() + "." + fileTypeName;
        String filePath = apkPath + newFilename;
        final InputStream inputStream;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        FileUtil.writeFromStream(inputStream, filePath);
        return apkLocation + newFilename;

    }

    /**
     * 构建查询条件
     * 根据查询参数构建MyBatis-Plus的查询条件
     *
     * @param query 查询参数，包含版本号、版本名称等
     * @return 查询条件
     */
    private LambdaQueryWrapper<EquipmentApk> builderWrapper(EquipmentApkQuery query) {
        LambdaQueryWrapper<EquipmentApk> wrapper = Wrappers.lambdaQuery();
        Long versionNo = query.getVersionNo();
        if (versionNo != null) {
            wrapper.eq(EquipmentApk::getVersionNo, versionNo);
        }
        String versionName = query.getVersionName();
        if (StrUtil.isNotBlank(versionName)) {
            wrapper.like(EquipmentApk::getVersionName, versionName);
        }
        wrapper.orderByDesc(EquipmentApk::getId);
        return wrapper;
    }
}