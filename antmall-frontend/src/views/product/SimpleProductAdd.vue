<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

const router = useRouter()

// 添加表单
const addForm = ref({
  name: '',
  description: '',
  unitPrice: 0,
  stockQuantity: 0
})

// 加载状态
const loading = ref(false)

// 后端API基础URL
const API_BASE_URL = 'http://localhost:9000'

// 创建Axios实例
const apiClient = axios.create({
  baseURL: API_BASE_URL,
  timeout: 10000
})

// 提交添加表单
const submitForm = async () => {
  // 表单验证
  if (!addForm.value.name.trim()) {
    alert('请输入商品名称')
    return
  }
  
  if (addForm.value.unitPrice < 0) {
    alert('商品单价不能为负数')
    return
  }
  
  if (addForm.value.stockQuantity < 0) {
    alert('库存数量不能为负数')
    return
  }
  
  loading.value = true
  try {
    // 调用后端API
    const response = await apiClient.post('/product/add', addForm.value)
    
    if (response.data.success) {
      alert('添加商品成功')
      // 跳转到商品列表页面
      router.push('/product/list')
    } else {
      alert('添加商品失败：' + response.data.message)
    }
  } catch (error) {
    console.error('添加商品失败:', error)
    alert('网络错误：无法连接到后端服务')
  } finally {
    loading.value = false
  }
}

// 重置表单
const resetForm = () => {
  addForm.value = {
    name: '',
    description: '',
    unitPrice: 0,
    stockQuantity: 0
  }
}

// 返回商品列表
const backToList = () => {
  router.push('/product/list')
}
</script>

<template>
  <div class="simple-product-add">
    <!-- 页面标题和操作按钮 -->
    <div class="page-header">
      <h2>添加商品 - 简化版</h2>
      <button @click="backToList">返回列表</button>
    </div>

    <!-- 添加表单 -->
    <div class="form-section">
      <h3>商品信息</h3>
      
      <div class="form-item">
        <label for="name">商品名称 *</label>
        <input 
          id="name"
          v-model="addForm.name" 
          placeholder="请输入商品名称"
          type="text"
          :disabled="loading"
        />
      </div>
      
      <div class="form-item">
        <label for="description">商品描述</label>
        <textarea 
          id="description"
          v-model="addForm.description" 
          placeholder="请输入商品描述"
          rows="3"
          :disabled="loading"
        ></textarea>
      </div>
      
      <div class="form-item">
        <label for="unitPrice">商品单价 *</label>
        <input 
          id="unitPrice"
          v-model.number="addForm.unitPrice" 
          placeholder="请输入商品单价"
          type="number"
          step="0.01"
          :disabled="loading"
        />
      </div>
      
      <div class="form-item">
        <label for="stockQuantity">库存数量 *</label>
        <input 
          id="stockQuantity"
          v-model.number="addForm.stockQuantity" 
          placeholder="请输入库存数量"
          type="number"
          step="1"
          :disabled="loading"
        />
      </div>
      
      <!-- 操作按钮 -->
      <div class="form-actions">
        <button 
          class="primary-btn" 
          @click="submitForm" 
          :disabled="loading"
        >
          {{ loading ? '提交中...' : '提交' }}
        </button>
        <button 
          @click="resetForm" 
          :disabled="loading"
        >
          重置
        </button>
        <button 
          @click="backToList" 
          :disabled="loading"
        >
          取消
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.simple-product-add {
  padding: 20px;
  font-family: Arial, sans-serif;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.page-header h2 {
  margin: 0;
  font-size: 24px;
  color: #333;
}

button {
  padding: 8px 16px;
  border: 1px solid #ccc;
  border-radius: 4px;
  background-color: #fff;
  cursor: pointer;
  font-size: 14px;
  margin-right: 10px;
}

button:hover {
  background-color: #f5f5f5;
}

button:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.primary-btn {
  background-color: #409eff;
  color: white;
  border: 1px solid #409eff;
}

.primary-btn:hover:not(:disabled) {
  background-color: #66b1ff;
}

.form-section {
  background-color: #f9f9f9;
  padding: 20px;
  border-radius: 8px;
  max-width: 600px;
}

.form-section h3 {
  margin-top: 0;
  margin-bottom: 20px;
  font-size: 18px;
  color: #333;
}

.form-item {
  margin-bottom: 20px;
}

.form-item label {
  display: block;
  margin-bottom: 8px;
  font-weight: bold;
  font-size: 14px;
  color: #555;
}

.form-item input,
.form-item textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 14px;
  box-sizing: border-box;
}

.form-item textarea {
  resize: vertical;
  min-height: 80px;
}

.form-actions {
  display: flex;
  gap: 10px;
  margin-top: 30px;
}
</style>