# 商品管理系统前端

基于 Vue 3 + Element Plus 开发的商品管理系统前端。

## 技术栈

- **核心框架**：Vue 3 (Composition API)
- **UI组件库**：Element Plus
- **构建工具**：Vite
- **HTTP客户端**：Axios
- **状态管理**：Pinia
- **路由管理**：Vue Router 4

## 功能特点

### 商品管理
- ✅ 商品列表展示
- ✅ 商品分页查询
- ✅ 商品搜索（按名称、描述）
- ✅ 商品添加
- ✅ 商品编辑
- ✅ 商品删除

### 技术特性
- ✅ 响应式设计
- ✅ 表单验证
- ✅ 加载状态提示
- ✅ 操作成功/失败提示
- ✅ 确认对话框
- ✅ 统一的API封装

## 项目结构

```
antmall-frontend/
├── public/                  # 静态资源
├── src/
│   ├── assets/             # 项目资源（图片、样式等）
│   ├── components/         # 通用组件
│   ├── views/              # 页面组件
│   │   └── product/        # 商品管理页面
│   ├── api/                # API请求封装
│   │   └── product.js      # 商品相关API
│   ├── stores/             # Pinia状态管理
│   │   └── product.js      # 商品状态
│   ├── router/             # 路由配置
│   │   └── index.js        # 路由定义
│   ├── utils/              # 工具函数
│   │   └── request.js      # Axios封装
│   ├── App.vue             # 根组件
│   └── main.js             # 入口文件
├── .env                    # 环境变量配置
├── .gitignore
├── index.html
├── package.json
├── vite.config.js
└── README.md
```

## 安装和运行

### 前置条件

- Node.js >= 16.0.0
- npm >= 7.0.0

### 安装依赖

```bash
npm install
```

### 开发模式

```bash
npm run dev
```

访问地址：http://localhost:5173

### 构建生产版本

```bash
npm run build
```

构建产物将输出到 `dist` 目录。

## 环境变量

在 `.env` 文件中配置：

```
# 开发环境配置
VITE_API_BASE_URL=http://localhost:9000
```

## API 对接

前端项目默认对接后端地址：`http://localhost:9000`

### 主要API

| 接口路径 | 方法 | 功能 |
| --- | --- | --- |
| /product/page | GET | 分页查询商品 |
| /product/add | POST | 添加商品 |
| /product/edit | POST | 编辑商品 |
| /product/delete | POST | 删除商品 |

## 学习要点

### 1. Vue 3 基础
- Composition API 的使用
- 响应式数据管理
- 组件生命周期

### 2. Element Plus
- 表单组件使用
- 表格组件使用
- 分页组件使用
- 消息提示组件

### 3. API 对接
- Axios 封装和拦截器
- 异步请求处理
- 错误处理

### 4. 状态管理
- Pinia 基本使用
- 状态定义和修改

### 5. 路由管理
- 路由配置
- 路由跳转
- 路由参数传递

## 开发建议

1. **代码风格**：遵循 Vue 3 官方最佳实践
2. **组件设计**：尽量拆分为可复用的小组件
3. **状态管理**：复杂状态使用 Pinia，简单状态使用组件内部响应式
4. **API 封装**：统一处理请求和响应
5. **错误处理**：合理处理各种异常情况
6. **用户体验**：添加加载状态、成功/失败提示

## 浏览器支持

- Chrome (最新版)
- Firefox (最新版)
- Safari (最新版)
- Edge (最新版)

## License

MIT
