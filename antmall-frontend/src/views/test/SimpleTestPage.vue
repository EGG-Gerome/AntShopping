<script setup>
import { ref } from 'vue'

const message = ref('极简测试页面 - 无第三方依赖')
const count = ref(0)

const increment = () => {
  count.value++
}

const decrement = () => {
  count.value--
}
</script>

<template>
  <div style="padding: 20px;">
    <h1>{{ message }}</h1>
    <p>这个页面不使用任何第三方库，只使用Vue的基础功能。</p>
    
    <div style="margin: 20px 0;">
      <h2>计数器：{{ count }}</h2>
      <div>
        <button @click="increment" style="margin-right: 10px;">+</button>
        <button @click="decrement">-</button>
      </div>
    </div>
    
    <div>
      <h3>测试结果：</h3>
      <ul>
        <li>Vue 3 基础功能：✅ 正常</li>
        <li>响应式数据：✅ 正常</li>
        <li>事件处理：✅ 正常</li>
        <li>模板渲染：✅ 正常</li>
      </ul>
    </div>
    
    <div style="margin-top: 30px; padding: 10px; background-color: #f0f2f5; border-radius: 5px;">
      <h3>调试信息：</h3>
      <p>如果能看到这个页面，说明Vue 3的基础功能正常工作。</p>
      <p>如果只能看到标题，说明路由或组件渲染有问题。</p>
      <p>如果完全看不到内容，说明Vue没有正常加载。</p>
    </div>
  </div>
</template>