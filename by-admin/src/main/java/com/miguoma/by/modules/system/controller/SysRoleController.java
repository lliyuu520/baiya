package com.miguoma.by.modules.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.lang.tree.Tree;
import com.miguoma.by.common.base.page.PageVO;
import com.miguoma.by.common.satoken.user.UserDetail;
import com.miguoma.by.common.utils.Result;
import com.miguoma.by.common.utils.SysUserUtil;
import com.miguoma.by.modules.system.convert.SysRoleConvert;
import com.miguoma.by.modules.system.dto.SysRoleDTO;
import com.miguoma.by.modules.system.entity.SysRole;
import com.miguoma.by.modules.system.query.SysRoleQuery;
import com.miguoma.by.modules.system.service.SysMenuService;
import com.miguoma.by.modules.system.service.SysRoleMenuService;
import com.miguoma.by.modules.system.service.SysRoleService;
import com.miguoma.by.modules.system.vo.SysRoleVO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色管理
 *
 * @author lliyuu520 lliyuu520@gmail.com
 */
@RestController
@RequestMapping("/sys/role")
@AllArgsConstructor
public class SysRoleController {
    private final SysRoleService sysRoleService;
    private final SysRoleMenuService sysRoleMenuService;
    private final SysMenuService sysMenuService;

    /**
     * 角色分页查询
     *
     * @param query 角色查询条件，包含分页参数和角色筛选条件
     * @return 返回分页后的角色列表数据
     */
    @GetMapping("page")
    @SaCheckPermission("sys:role:page")
    public Result<PageVO<SysRoleVO>> page(final SysRoleQuery query) {
        final PageVO<SysRoleVO> page = sysRoleService.page(query);

        return Result.ok(page);
    }

    /**
     * 获取角色列表
     *
     * @return 返回所有角色列表数据
     */
    @GetMapping("list")
    @SaCheckPermission("sys:role:list")
    public Result<List<SysRoleVO>> list() {
        final List<SysRoleVO> list = sysRoleService.getList(new SysRoleQuery());

        return Result.ok(list);
    }

    /**
     * 根据ID获取角色详情
     *
     * @param id 角色ID
     * @return 返回角色详细信息，包含角色基本信息和关联的菜单ID列表
     */
    @GetMapping("{id}")
    @SaCheckPermission("sys:role:info")
    public Result<SysRoleVO> get(@PathVariable("id") final Long id) {
        final SysRole entity = sysRoleService.getById(id);

        // 转换对象
        final SysRoleVO role = SysRoleConvert.INSTANCE.convertTOVO(entity);

        // 查询角色对应的菜单
        final List<Long> menuIdList = sysRoleMenuService.getMenuIdList(id);
        role.setMenuIdList(menuIdList);

        return Result.ok(role);
    }

    /**
     * 新增角色
     *
     * @param dto 角色信息，包含角色基本信息和关联的菜单ID列表
     * @return 返回操作结果
     */
    @PostMapping
    @SaCheckPermission("sys:role:save")
    public Result<String> save(@RequestBody final SysRoleDTO dto) {
        sysRoleService.save(dto);

        return Result.ok();
    }

    /**
     * 修改角色
     *
     * @param dto 角色信息，包含角色基本信息和关联的菜单ID列表
     * @return 返回操作结果
     */
    @PutMapping
    @SaCheckPermission("sys:role:update")
    public Result<String> update(@RequestBody final SysRoleDTO dto) {
        sysRoleService.update(dto);

        return Result.ok();
    }

    /**
     * 删除角色
     *
     * @param id 角色ID
     * @return 返回操作结果
     */
    @DeleteMapping
    @SaCheckPermission("sys:role:delete")
    public Result<String> delete(@RequestBody final Long id) {
        sysRoleService.deleteOne(id);

        return Result.ok();
    }

    /**
     * 获取当前用户的菜单树
     *
     * @return 返回当前用户有权限访问的菜单树结构
     */
    @GetMapping("menu")
    @SaCheckPermission("sys:role:menu")
    public Result<List<Tree<Long>>> menu() {
        final UserDetail user = SysUserUtil.getUserInfo();
        final List<Tree<Long>> list = sysMenuService.getUserMenuList(user, null);

        return Result.ok(list);
    }

    /**
     * 保存字段权限
     *
     * @param dto
     * @return
     */
    @PostMapping("/saveField")
    public Result<Boolean> saveField(@RequestBody final SysRoleDTO dto) {
        sysRoleService.saveField(dto);
        return Result.ok();

    }

    /**
     * 获取字段权限
     * 
     * @param id 角色ID
     * @return
     */
    @GetMapping("/getFieldIdByRoleId")
    public Result<List<Long>> getFieldIdByRoleId(final Long id) {
        List<Long> list = sysRoleService.getFieldByRoleId(id);
        return Result.ok(list);
    }

    /**
     * 获取字段权限
     *
     * 该方法用于获取当前用户在其角色下所拥有的字段权限
     * 它首先获取用户的详细信息，然后提取用户的角色ID列表，
     * 并基于这些角色ID列表获取相应的字段权限列表
     *
     * @return 返回一个Result对象，其中包含用户拥有的字段权限列表
     */
    @GetMapping("/getFieldPermission")
    public Result<List<String>> getFieldPermission() {
        // 获取当前用户信息
        final UserDetail userInfo = SysUserUtil.getUserInfo();
        // 提取用户的角色ID列表
        final List<Long> roleIdList = userInfo.getRoleIdList();
        // 根据角色ID列表获取字段权限列表
        List<String> list = sysRoleService.getFieldPermission(roleIdList);
        // 返回包含字段权限列表的Result对象
        return Result.ok(list);
    }


}
