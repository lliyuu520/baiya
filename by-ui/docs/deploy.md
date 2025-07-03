# 构建与部署说明

## 环境依赖
- Node.js 16.x 及以上
- Yarn 或 npm
- 支持 ES6+ 的现代浏览器

## 依赖安装
```bash
yarn install
# 或
npm install
```

## 开发环境运行
```bash
yarn dev
# 或
npm run dev
```

## 生产环境构建
```bash
yarn build
# 或
npm run build
```

## 环境变量
- .env、.env.development、.env.production 配置不同环境变量。
- VITE_ 前缀变量可在代码中通过 import.meta.env 访问。

## 部署流程
- 构建后 dist/ 目录为静态资源，可部署至 nginx、静态服务器等。
- 配置 nginx 反向代理后端 API。

## 常见问题
- 依赖安装失败：检查 node 版本与网络。
- 端口冲突：修改 vite.config.ts 中 server.port。
- 构建报错：检查依赖版本与配置。

---

> 本文档自动生成，具体细节请结合实际部署环境与团队运维规范。 