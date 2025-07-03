# ERP 模块文档

## 模块简介

ERP（企业资源计划）模块负责与外部 ERP 系统的数据对接与同步，实现生产部门、产品类别、产品、订单等核心业务数据的批量导入和同步，是企业信息化集成的重要桥梁。

## 主要职责

- 批量同步生产部门信息
- 批量同步产品类别信息
- 批量同步产品信息
- 同步生产订单信息
- 对接外部 ERP 系统，保障数据一致性

## 主要实体类

- ErpDepartDTO：ERP 生产部门数据传输对象
- ErpProductCategoryDTO：ERP 产品类别数据传输对象
- ErpProductDTO：ERP 产品数据传输对象
- ErpOrderDTO：ERP 订单数据传输对象

## 主要接口（Controller）

### ERP 对接接口（ErpController）

- `POST /erp/productionDepart/batchCreate`：批量创建生产部门（参数：List<ErpDepartDTO>）
- `POST /erp/productCategory/batchCreate`：批量创建产品类别（参数：List<ErpProductCategoryDTO>）
- `POST /erp/product/batchCreate`：批量创建产品（参数：List<ErpProductDTO>）
- `POST /erp/order/create`：创建生产订单（参数：ErpOrderDTO）

> 所有接口均支持日志切面（@ErpLogCut）和签名校验（@SignatureCheck），部分接口可配置是否强制签名。

## 典型业务流程

- 外部 ERP 系统推送数据 → 通过接口批量导入 → 生产/产品/订单等数据同步到本地业务表 → 日志记录
- 数据同步过程支持幂等性校验和异常处理，保障数据一致性

## 相关注解与切面

- `@ErpLogCut`：ERP 操作日志切面
- `@SignatureCheck`：接口签名校验，保障数据安全
- `@SaIgnore`：跳过权限校验（对接接口专用）

---

> 本文档自动生成，详细字段与接口参数请参考具体实体类与 Controller 源码。
