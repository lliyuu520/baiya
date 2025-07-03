# Record 模块文档

## 模块简介

Record（记录管理）模块负责生产过程中的码（如箱码、袋码、二维码等）记录的采集、查询与管理，是生产追溯与数据归档的基础。

## 主要职责

- 生产过程码（箱码、袋码、二维码等）记录
- 记录的分页查询与归档
- 与生产、设备等模块的数据联动

## 主要实体类

- RecordBoxCode：箱码记录实体
- RecordBagCode：袋码记录实体
- RecordQrCode：二维码记录实体
- RecordBoxCodeReplace/RecordQrCodeReplace：码替换记录实体

## 主要接口（Controller）

### 箱码记录接口（RecordBoxCodeController）

- `GET /record/boxCode/page`：分页查询箱码记录

### 其他记录接口

- 袋码、二维码、替换等均有对应的分页查询接口，路径如 `/record/bagCode/page`、`/record/qrCode/page`、`/record/boxCodeReplace/page`、`/record/qrCodeReplace/page` 等。

## 典型业务流程

- 生产过程扫码 → 记录采集 → 数据归档与追溯

## 相关注解与切面

- `@SaCheckPermission`：接口权限控制
- `@SysLogCut`：操作日志切面

---

> 本文档自动生成，详细字段与接口参数请参考具体实体类与 Controller 源码。
