<script setup>
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import axios from 'axios'

const router = useRouter()
const route = useRoute()

// 商品ID
const productId = ref(route.params.id)

// 编辑表单
const editForm = ref({
  id: '',
  name: '',
  description: '',
  unitPrice: 0,
  stockQuantity: 0
})

// 加载状态
const loading = ref(false)
const loadingDetail = ref(false)

// 后端API基础URL
const API_BASE_URL = 'http://localhost:9000'

// 创建Axios实例
const apiClient = axios.create({
  baseURL: API_BASE_URL,
  timeout: 10000
})

// 获取商品详情
const getProductDetail = async () => {
  loadingDetail.value = true
  try {
    // 从列表中查找商品
    // 调用列表接口获取所有商品，然后查找当前商品
    const response = await apiClient.get('/product/page', {
      params: {
        currentPage: 1,
        pageSize: 1000 // 获取足够多的商品，确保能找到当前商品
      }
    })
    
    if (response.data.success) {
      const products = response.data.data.dataList || []
      const product = products.find(p => p.id === productId.value)
      
      if (product) {
        // 回显商品信息到表单
        editForm.value = {
          id: product.id,
          name: product.name,
          description: product.description,
          unitPrice: product.unitPrice,
          stockQuantity: product.stockQuantity
        }
      } else {
        alert('商品不存在或已被删除')
        router.push('/product/list')
      }
    } else {
      alert('获取商品列表失败：' + response.data.message)
    }
  } catch (error) {
    console.error('获取商品详情失败:', error)
    alert('网络错误：无法连接到后端服务')
  } finally {
    loadingDetail.value = false
  }
}

// 页面加载时获取商品详情
onMounted(() => {
  getProductDetail()
})

// 提交编辑表单
const submitForm = async () => {
  // 表单验证
  if (!editForm.value.name.trim()) {
    alert('请输入商品名称')
    return
  }
  
  if (editForm.value.unitPrice < 0) {
    alert('商品单价不能为负数')
    return
  }
  
  if (editForm.value.stockQuantity < 0) {
    alert('库存数量不能为负数')
    return
  }
  
  loading.value = true
  try {
    // 调用后端API
    const response = await apiClient.post('/product/edit', editForm.value)
    
    if (response.data.success) {
      alert('编辑商品成功')
      // 跳转到商品列表页面
      router.push('/product/list')
    } else {
      alert('编辑商品失败：' + response.data.message)
    }
  } catch (error) {
    console.error('编辑商品失败:', error)
    alert('网络错误：无法连接到后端服务')
  } finally {
    loading.value = false
  }
}

// 重置表单
const resetForm = () => {
  getProductDetail()
}

// 返回商品列表
const backToList = () => {
  router.push('/product/list')
}
</script>

<template>
  <div class="simple-product-edit">
    <!-- 页面标题和操作按钮 -->
    <div class="page-header">
      <h2>编辑商品 - 简化版</h2>
      <button @click="backToList">返回列表</button>
    </div>

    <!-- 编辑表单 -->
    <div class="form-section">
      <h3>商品信息</h3>
      
      <div v-if="loadingDetail" class="loading">加载商品详情中...</div>
      
      <div v-else class="form-content">
        <div class="form-item">
          <label for="name">商品名称 *</label>
          <input 
            id="name"
            v-model="editForm.name" 
            placeholder="请输入商品名称"
            type="text"
            :disabled="loading"
          />
        </div>
        
        <div class="form-item">
          <label for="description">商品描述</label>
          <textarea 
            id="description"
            v-model="editForm.description" 
            placeholder="请输入商品描述"
            rows="3"
            :disabled="loading"
          ></textarea>
        </div>
        
        <div class="form-item">
          <label for="unitPrice">商品单价 *</label>
          <input 
            id="unitPrice"
            v-model.number="editForm.unitPrice" 
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
            v-model.number="editForm.stockQuantity" 
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
            {{ loading ? '保存中...' : '保存' }}
          </button>
          <button 
            @click="resetForm" 
            :disabled="loading || loadingDetail"
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
  </div>
</template>

<style scoped>
.simple-product-edit {
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

.loading {
  text-align: center;
  padding: 20px;
  color: #666;
  font-style: italic;
}

.form-content {
  display: block;
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