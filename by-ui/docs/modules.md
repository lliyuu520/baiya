# 主要模块与页面说明

## 页面模块（views）
- 按业务领域划分，如 equipment、erp、monitor、production、record、sys 等，每个目录下为对应业务页面。
- 典型页面如：登录、首页、各业务管理页（如生产订单、设备管理、日志管理等）。

## 公共组件（components）
- fast-radio-group、fast-select、fast-table-column、fast-user、svg-icon 等为全局/通用组件。
- 支持按需引入与复用，提升开发效率。

## API 封装（api）
- 按业务模块划分，每个子目录对应后端一个领域（如 production、equipment、sys 等）。
- 统一封装请求、响应、错误处理，便于维护。

## 路由配置（router）
- 基于 Vue Router 4.x，支持动态路由与权限控制。
- 路由与菜单解耦，支持多级菜单与页面缓存。

## 状态管理（store）
- 采用 Pinia，分模块管理用户、路由、标签页、主题等状态。
- 支持持久化与动态权限。

## 工具与配置
- utils 提供常用工具函数（如缓存、校验、主题、请求等）。
- config 目录集中管理全局配置。

---

> 本文档自动生成，详细结构请结合实际目录与源码查阅。 