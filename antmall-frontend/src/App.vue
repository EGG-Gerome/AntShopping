<script setup>
// App.vue 作为应用根组件
</script>

<template>
  <div class="app-container">
    <!-- 页面头部 -->
    <header class="app-header">
      <h1>商品管理系统</h1>
    </header>
    
    <!-- 主内容区域 -->
    <main class="app-main">
      <router-view></router-view>
    </main>
    
    <!-- 页面底部 -->
    <footer class="app-footer">
      <p>© 2025 商品管理系统</p>
    </footer>
  </div>
</template>

<style>
/* 全局样式重置 */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
  background-color: #f5f7fa;
  color: #333;
  line-height: 1.6;
}

/* 应用容器 */
.app-container {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

/* 页面头部 */
.app-header {
  background-color: #409eff;
  color: white;
  padding: 1rem 2rem;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.app-header h1 {
  font-size: 1.5rem;
  font-weight: 600;
}

/* 主内容区域 */
.app-main {
  flex: 1;
  padding: 2rem;
  max-width: 1200px;
  width: 100%;
  margin: 0 auto;
  width: 100%;
}

/* 页面底部 */
.app-footer {
  background-color: #f0f2f5;
  text-align: center;
  padding: 1rem;
  color: #606266;
  font-size: 0.9rem;
  margin-top: auto;
}

/* 卡片样式 */
.card {
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  padding: 1.5rem;
  margin-bottom: 1.5rem;
}

/* 按钮容器 */
.btn-container {
  display: flex;
  gap: 0.5rem;
  margin-bottom: 1rem;
}

/* 表单样式 */
.form-item {
  margin-bottom: 1rem;
}

.form-item label {
  display: block;
  margin-bottom: 0.5rem;
  font-weight: 500;
  color: #303133;
}
</style>
