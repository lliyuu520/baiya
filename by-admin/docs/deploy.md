# 部署与运维说明

## 环境依赖

- JDK 17 及以上
- Maven 3.6 及以上
- Redis 服务
- Oracle 数据库

## 配置项

- 修改 src/main/resources/application.yml，配置数据库、Redis、端口等信息。
- 生产环境建议开启 HTTPS，配置日志路径与备份策略。

## 编译与启动

```bash
# 编译
mvn clean package
# 运行
java -jar target/by-admin.jar
```

## 日志与监控

- 日志文件路径见 logback-spring.xml 配置。
- 建议定期清理日志，防止磁盘占满。
- 可通过 monitor 模块接口监控 Redis、服务器状态。

## 常见问题

- 数据库连接失败：检查 application.yml 配置与数据库服务状态。
- Redis 连接失败：检查 Redis 服务与配置。
- 端口占用：修改 application.yml 中 server.port。
- 启动慢或报错：检查依赖环境与日志输出。

## 备份与恢复

- 定期备份数据库与重要配置文件。
- 日志建议定期归档。

---

> 本文档自动生成，具体细节请结合实际部署环境与团队运维规范。
