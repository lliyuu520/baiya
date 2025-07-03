# Monitor 模块文档

## 模块简介

Monitor（系统监控）模块负责对系统运行状态、缓存（Redis）等进行监控与管理，保障平台稳定运行。

## 主要职责

- Redis 缓存监控与管理
- 服务器运行状态监控
- 缓存清理与 Key 管理

## 主要实体类

- Cache：缓存信息实体
- Server：服务器状态信息实体（如有）

## 主要接口（Controller）

### 缓存监控接口（CacheController）

- `GET /monitor/cache/info`：获取 Redis 详情
- `GET /monitor/cache/getCacheKeys`：获取所有 Key
- `GET /monitor/cache/getCacheKeys/{cacheKey}`：获取结构化键下的 Key
- `GET /monitor/cache/getCacheValue/{cacheKey}`：获取指定键的值
- `DELETE /monitor/cache/delCacheKey/{cacheKey}`：删除指定键
- `DELETE /monitor/cache/delCacheKeys/{cacheKey}`：删除结构化键下的缓存
- `DELETE /monitor/cache/delCacheAll`：删除全部缓存

### 服务器监控接口（ServerController）

- `GET /monitor/server/info`：获取服务器状态信息（如有）

## 典型业务流程

- 运维人员通过平台监控 Redis、服务器状态，进行缓存清理与异常排查

## 相关注解与切面

- `@SaCheckPermission`：接口权限控制

---

> 本文档自动生成，详细字段与接口参数请参考具体实体类与 Controller 源码。
