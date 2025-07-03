# 数据库结构文档

本文件自动汇总所有实体类的表结构、字段说明、主键、逻辑删除、关联关系等，便于数据库设计与维护。

---

## 通用字段（BaseEntity）

- id：主键，Long，自增/分配
- createTime：创建时间，LocalDateTime
- updateTime：更新时间，LocalDateTime
- creator：创建者，Long
- updater：更新者，Long
- deleted：逻辑删除标记，Integer，@TableLogic

---

## 主要业务实体（示例）

### SysUser（系统用户）

- id：主键
- username：用户名
- password：密码
- status：状态
- roleIdList：角色 ID 列表
- ...

### ProductionOrder（生产订单）

- id：主键
- orderNo：订单号
- productId：产品 ID
- status：订单状态
- ...

### EquipmentClient（设备客户端）

- id：主键
- clientName：客户端名称
- version：版本号
- ...

### RecordBoxCode（箱码记录）

- id：主键
- boxCode：箱码
- orderId：订单 ID
- ...

---

> 本文档自动生成，详细字段请参考各实体类源码与数据库表结构。
