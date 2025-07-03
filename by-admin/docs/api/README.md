# API 接口文档总览

本文件自动汇总后端所有 RESTful 接口，按模块分类，便于前后端协作与接口对接。

---

## System 模块

### 用户管理（SysUserController）

- `GET /sys/user/page`：分页查询用户列表（权限：sys:user:page）
- `GET /sys/user/{id}`：获取用户信息（权限：sys:user:info）
- `GET /sys/user/info`：获取当前登录用户信息
- `PUT /sys/user/password`：修改密码
- `POST /sys/user`：新增用户（权限：sys:user:save）
- `PUT /sys/user`：编辑用户（权限：sys:user:update）
- `DELETE /sys/user`：删除用户（权限：sys:user:delete）

### 角色管理（SysRoleController）

- `GET /sys/role/page`：分页查询角色列表
- `POST /sys/role`：新增角色
- `PUT /sys/role`：编辑角色
- `DELETE /sys/role`：删除角色
- `GET /sys/role/{id}`：获取角色详情

### 菜单管理（SysMenuController）

- `GET /sys/menu/list`：获取菜单树
- `POST /sys/menu`：新增菜单
- `PUT /sys/menu`：编辑菜单
- `DELETE /sys/menu`：删除菜单

### 日志管理（SysLogController）

- `GET /sys/log/page`：分页查询操作日志
- `GET /sys/log/{id}`：获取日志详情

### 表配置（SysTableConfigController, SysTableFieldController, CodeRuleController）

- `GET /sys/tableConfig/page`：分页查询表配置
- `POST /sys/tableConfig`：新增表配置
- `PUT /sys/tableConfig`：编辑表配置
- `DELETE /sys/tableConfig`：删除表配置
- `GET /sys/tableField/page`：分页查询字段配置
- `POST /sys/tableField`：新增字段配置
- `PUT /sys/tableField`：编辑字段配置
- `DELETE /sys/tableField`：删除字段配置
- `GET /sys/codeRule/page`：分页查询编码规则

---

## ERP 模块

### ERP 对接（ErpController）

- `POST /erp/productionDepart/batchCreate`：批量创建生产部门
- `POST /erp/productCategory/batchCreate`：批量创建产品类别
- `POST /erp/product/batchCreate`：批量创建产品
- `POST /erp/order/create`：创建生产订单

---

## Production 模块

### 生产订单（ProductionOrderController）

- `GET /production/order/page`：分页查询生产订单列表
- `POST /production/order`：新增生产订单
- `PUT /production/order`：编辑生产订单
- `DELETE /production/order/delete`：删除生产订单
- `GET /production/order/info`：获取生产订单详情
- `GET /production/order/list`：获取生产订单列表
- `POST /production/order/rework`：返工
- `POST /production/order/cancelRework`：取消返工

### 其他生产相关接口

- 工厂、班组、班次、产品、类别等均有对应的增删改查接口，路径如 `/production/factory`、`/production/team`、`/production/shift`、`/production/product`、`/production/productCategory` 等。

---

## Equipment 模块

### 设备客户端（EquipmentClientController）

- `GET /equipment/client/page`：分页查询 Client 版本列表
- `POST /equipment/client/updatePassword`：修改密码
- `GET /equipment/client/info`：获取 Client 信息

### 设备 APK（EquipmentApkController）

- `GET /equipment/apk/page`：分页查询 APK 版本
- `POST /equipment/apk`：新增 APK 版本
- `PUT /equipment/apk`：编辑 APK 版本
- `DELETE /equipment/apk`：删除 APK 版本

---

## Record 模块

### 箱码记录（RecordBoxCodeController）

- `GET /record/boxCode/page`：分页查询箱码记录

### 其他记录接口

- 袋码、二维码、替换等均有对应的分页查询接口，路径如 `/record/bagCode/page`、`/record/qrCode/page`、`/record/boxCodeReplace/page`、`/record/qrCodeReplace/page` 等。

---

## Monitor 模块

### 缓存监控（CacheController）

- `GET /monitor/cache/info`：获取 Redis 详情
- `GET /monitor/cache/getCacheKeys`：获取所有 Key
- `GET /monitor/cache/getCacheKeys/{cacheKey}`：获取结构化键下的 Key
- `GET /monitor/cache/getCacheValue/{cacheKey}`：获取指定键的值
- `DELETE /monitor/cache/delCacheKey/{cacheKey}`：删除指定键
- `DELETE /monitor/cache/delCacheKeys/{cacheKey}`：删除结构化键下的缓存
- `DELETE /monitor/cache/delCacheAll`：删除全部缓存

### 服务器监控（ServerController）

- `GET /monitor/server/info`：获取服务器状态信息（如有）

---

> 本文档自动生成，详细参数与返回值结构请参考各模块详细文档与 Controller 源码。
