# System 模块文档

## 模块简介

System（系统管理）模块负责平台的用户、角色、菜单、权限、日志、表配置等核心系统功能的管理，是后台管理系统的基础支撑模块。

## 主要职责

- 用户管理（增删改查、密码修改、角色分配等）
- 角色管理（增删改查、权限分配）
- 菜单管理（菜单、权限点维护）
- 日志管理（操作日志、系统日志）
- 表配置（自定义表结构、字段配置）

## 主要实体类

- SysUser：系统用户实体，包含用户名、密码、角色、状态等字段。
- SysRole：系统角色实体，定义权限集合。
- SysMenu：系统菜单与权限点实体。
- SysLog：系统操作日志实体。
- SysTableConfig / SysTableField：自定义表与字段配置实体。

## 主要接口（Controller）

### 用户管理接口（SysUserController）

- `GET /sys/user/page`：分页查询用户列表（权限：sys:user:page）
- `GET /sys/user/{id}`：获取用户信息（权限：sys:user:info）
- `GET /sys/user/info`：获取当前登录用户信息
- `PUT /sys/user/password`：修改密码
- `POST /sys/user`：新增用户（权限：sys:user:save）
- `PUT /sys/user`：编辑用户（权限：sys:user:update）
- `DELETE /sys/user`：删除用户（权限：sys:user:delete）

### 角色管理接口（SysRoleController）

- `GET /sys/role/page`：分页查询角色列表
- `POST /sys/role`：新增角色
- `PUT /sys/role`：编辑角色
- `DELETE /sys/role`：删除角色
- `GET /sys/role/{id}`：获取角色详情

### 菜单管理接口（SysMenuController）

- `GET /sys/menu/list`：获取菜单树
- `POST /sys/menu`：新增菜单
- `PUT /sys/menu`：编辑菜单
- `DELETE /sys/menu`：删除菜单

### 日志管理接口（SysLogController）

- `GET /sys/log/page`：分页查询操作日志
- `GET /sys/log/{id}`：获取日志详情

### 表配置接口（SysTableConfigController, SysTableFieldController, CodeRuleController）

- `GET /sys/tableConfig/page`：分页查询表配置
- `POST /sys/tableConfig`：新增表配置
- `PUT /sys/tableConfig`：编辑表配置
- `DELETE /sys/tableConfig`：删除表配置
- `GET /sys/tableField/page`：分页查询字段配置
- `POST /sys/tableField`：新增字段配置
- `PUT /sys/tableField`：编辑字段配置
- `DELETE /sys/tableField`：删除字段配置
- `GET /sys/codeRule/page`：分页查询编码规则

## 典型业务流程

- 用户注册/登录 → 权限校验 → 菜单/角色分配 → 操作日志记录
- 管理员通过界面维护用户、角色、菜单、表结构等，所有操作均有日志切面记录

## 相关注解与切面

- `@SaCheckPermission`：接口权限控制
- `@SysLogCut`：操作日志切面

---

> 本文档自动生成，详细字段与接口参数请参考具体实体类与 Controller 源码。
