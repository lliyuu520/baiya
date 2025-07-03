# BY-Admin 项目架构总览

## 项目简介

BY-Admin 是一个基于 Spring Boot 3.x 的现代化后台管理系统，采用分层架构和主流技术栈，服务于企业级生产、设备、ERP、系统管理等多业务场景。

## 技术栈与依赖

- **核心框架**：Spring Boot 3.0.4
- **安全框架**：Sa-Token 1.42.0
- **持久层**：MyBatis-Plus 3.5.3.1
- **数据库**：Oracle
- **缓存**：Redis
- **工具库**：Hutool 5.8.25
- **API 文档**：SpringDoc 1.6.15
- **对象映射**：MapStruct 1.5.3.Final
- **日志**：TLog 1.5.2

## 目录结构与分层说明

```
by-admin/
├── src/main/java/com/miguoma/by
│   ├── modules        # 业务模块（system、erp、production、equipment、record、monitor等）
│   ├── common         # 公共基础设施（实体、工具、注解、切面、异常、缓存、配置等）
│   └── ByAdminApplication.java  # 启动类
├── src/main/resources # 配置文件、MyBatis-Plus Mapper
├── sql               # SQL脚本
└── pom.xml           # 项目依赖
```

### 分层结构

- **Controller 层**：对外 RESTful 接口，参数校验、权限控制、日志切面。
- **Service 层**：核心业务逻辑实现，事务控制。
- **Entity/DTO/VO/Query**：领域实体、数据传输对象、视图对象、查询对象分层，提升解耦。
- **Mapper 层**：MyBatis-Plus 持久化操作，SQL 映射。
- **Common 公共层**：基础实体、工具、注解、切面、异常、缓存、配置等全局通用能力。

## 主要业务模块总览

- **system**：系统管理（用户、角色、菜单、日志、表配置等）
- **erp**：ERP 对接（生产部门、产品、订单等数据同步）
- **production**：生产管理（订单、工厂、班组、产品、类别等）
- **equipment**：设备管理（客户端、APK 版本等）
- **record**：生产记录（箱码、袋码、二维码等）
- **monitor**：系统监控（缓存、服务器状态等）

## 基础设施说明

- **安全与权限**：Sa-Token 统一认证与权限注解，关键接口权限保护。
- **日志与切面**：自定义注解+切面，统一操作日志、异常日志。
- **异常处理**：全局异常捕获与友好响应。
- **缓存**：Redis 集成，支持缓存监控与清理。
- **API 统一响应**：Result 封装，标准化接口返回。

## 核心设计思想

- 分层解耦，职责清晰，便于维护与扩展。
- 领域驱动，实体/DTO/VO/Query 分层，提升可读性与可测试性。
- 注解驱动，权限、日志、异常等横切关注点统一管理。
- 自动化与规范化，接口、实体、SQL、日志等均有统一规范。

---

> 本文档为自动生成，后续可结合各模块文档、接口文档、数据库文档等进一步细化。
