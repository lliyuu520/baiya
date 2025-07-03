# Production 模块文档

## 模块简介

Production（生产管理）模块负责生产订单、工厂、班组、产品、类别等生产相关核心业务的全生命周期管理，是企业生产流程数字化的核心。

## 主要职责

- 生产订单管理（增删改查、返工、取消返工等）
- 工厂、班组、班次、产品、产品类别等基础数据维护
- 生产流程与状态流转管理
- 支持与 ERP、设备等模块的数据联动

## 主要实体类

- ProductionOrder：生产订单实体
- ProductionFactory：工厂实体
- ProductionTeam：班组实体
- ProductionShift：班次实体
- ProductionProduct：产品实体
- ProductionProductCategory：产品类别实体

## 主要接口（Controller）

### 生产订单接口（ProductionOrderController）

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

## 典型业务流程

- 生产订单创建 → 状态流转（如返工/取消返工）→ 生产数据归档
- 基础数据维护 → 生产流程配置 → 订单与班组、产品等关联

## 相关注解与切面

- `@SaCheckPermission`：接口权限控制
- `@SysLogCut`：操作日志切面

---

> 本文档自动生成，详细字段与接口参数请参考具体实体类与 Controller 源码。
