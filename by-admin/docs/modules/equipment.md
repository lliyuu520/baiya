# Equipment 模块文档

## 模块简介

Equipment（设备管理）模块负责设备客户端、APK 版本等相关设备资产的管理与维护，支撑生产现场的数字化与自动化。

## 主要职责

- 设备客户端管理（版本发布、查询、更新、密码修改等）
- 设备 APK 版本管理
- 设备与生产、记录等模块的数据联动

## 主要实体类

- EquipmentClient：设备客户端实体
- EquipmentApk：设备 APK 版本实体

## 主要接口（Controller）

### 设备客户端接口（EquipmentClientController）

- `GET /equipment/client/page`：分页查询 Client 版本列表
- `POST /equipment/client/updatePassword`：修改密码
- `GET /equipment/client/info`：获取 Client 信息

### 设备 APK 接口（EquipmentApkController）

- `GET /equipment/apk/page`：分页查询 APK 版本
- `POST /equipment/apk`：新增 APK 版本
- `PUT /equipment/apk`：编辑 APK 版本
- `DELETE /equipment/apk`：删除 APK 版本

## 典型业务流程

- 设备客户端注册/升级 → 版本管理 → 生产现场设备与系统联动

## 相关注解与切面

- `@SaCheckPermission`：接口权限控制
- `@SysLogCut`：操作日志切面

---

> 本文档自动生成，详细字段与接口参数请参考具体实体类与 Controller 源码。
