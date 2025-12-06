<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const message = ref('测试页面 - 路由和组件渲染正常！')

const goToProductList = () => {
  router.push('/product/list')
}
</script>

<template>
  <div class="test-page">
    <h2>{{ message }}</h2>
    <p>这是一个简单的测试页面，用于验证路由和组件渲染是否正常工作。</p>
    <button @click="goToProductList">返回商品列表</button>
    <div style="margin-top: 20px;">
      <h3>测试内容：</h3>
      <ul>
        <li>路由配置：✅ 正常</li>
        <li>组件渲染：✅ 正常</li>
        <li>按钮点击：✅ 正常</li>
      </ul>
    </div>
  </div>
</template>

<style scoped>
.test-page {
  padding: 20px;
  background-color: #f0f2f5;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

button {
  padding: 8px 16px;
  background-color: #409eff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
}

button:hover {
  background-color: #66b1ff;
}
</style>