<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

const router = useRouter()

// 商品列表数据
const products = ref([])
// 加载状态
const loading = ref(false)
// 搜索表单
const searchForm = ref({
  name: '',
  description: ''
})

// 分页数据
const pagination = ref({
  currentPage: 1,
  pageSize: 10,
  total: 0
})

// 后端API基础URL
const API_BASE_URL = 'http://localhost:9000'

// 创建Axios实例
const apiClient = axios.create({
  baseURL: API_BASE_URL,
  timeout: 10000
})

// 获取商品列表
const getProducts = async (page = 1) => {
  loading.value = true
  try {
    // 构建查询参数
    const params = {
      currentPage: page,
      pageSize: pagination.value.pageSize
    }
    
    // 添加搜索条件
    if (searchForm.value.name) {
      params.name = searchForm.value.name
    }
    
    if (searchForm.value.description) {
      params.description = searchForm.value.description
    }
    
    // 调用后端API
    const response = await apiClient.get('/product/page', {
      params
    })
    
    if (response.data.success) {
      // 处理响应数据
      const data = response.data.data
      products.value = data.dataList || []
      pagination.value.total = data.totalSize || 0
      pagination.value.currentPage = page
    } else {
      alert('获取商品列表失败：' + response.data.message)
    }
  } catch (error) {
    console.error('获取商品列表失败:', error)
    alert('网络错误：无法连接到后端服务')
  } finally {
    loading.value = false
  }
}

// 页面加载时获取商品列表
onMounted(() => {
  getProducts()
})

// 搜索商品
const searchProducts = () => {
  getProducts(1) // 搜索时从第一页开始
}

// 重置搜索
const resetSearch = () => {
  searchForm.value = {
    name: '',
    description: ''
  }
  getProducts(1) // 重置后从第一页开始
}

// 跳转到添加商品页面
const goToAdd = () => {
  router.push('/product/add')
}

// 跳转到编辑商品页面
const goToEdit = (id) => {
  router.push(`/product/edit/${id}`)
}

// 删除商品
const deleteProduct = async (id) => {
  if (confirm('确定要删除该商品吗？')) {
    try {
      // 调用后端API删除商品
      const response = await apiClient.post('/product/delete', [id])
      
      if (response.data.success) {
        alert('删除成功')
        // 重新获取商品列表
        getProducts(pagination.value.currentPage)
      } else {
        alert('删除失败：' + response.data.message)
      }
    } catch (error) {
      console.error('删除商品失败:', error)
      alert('网络错误：无法连接到后端服务')
    }
  }
}

// 分页变化处理
const handlePageChange = (page) => {
  getProducts(page)
}

// 页面大小变化处理
const handleSizeChange = (size) => {
  pagination.value.pageSize = size
  getProducts(1)
}
</script>

<template>
  <div class="simple-product-list">
    <!-- 页面标题和操作按钮 -->
    <div class="page-header">
      <h2>商品列表 - 简化版</h2>
      <button class="primary-btn" @click="goToAdd">添加商品</button>
    </div>

    <!-- 搜索表单 -->
    <div class="search-section">
      <h3>搜索条件</h3>
      <div class="search-form">
        <div class="form-item">
          <label>商品名称:</label>
          <input 
            v-model="searchForm.name" 
            placeholder="请输入商品名称"
            type="text"
          />
        </div>
        <div class="form-item">
          <label>商品描述:</label>
          <input 
            v-model="searchForm.description" 
            placeholder="请输入商品描述"
            type="text"
          />
        </div>
        <div class="form-actions">
          <button @click="searchProducts">搜索</button>
          <button @click="resetSearch">重置</button>
        </div>
      </div>
    </div>

    <!-- 商品列表 -->
    <div class="product-table-section">
      <h3>商品列表</h3>
      
      <!-- 加载状态 -->
      <div v-if="loading" class="loading">加载中...</div>
      
      <!-- 商品表格 -->
      <table v-else class="product-table">
        <thead>
          <tr>
            <th>商品ID</th>
            <th>商品名称</th>
            <th>商品描述</th>
            <th>单价</th>
            <th>库存</th>
            <th>创建时间</th>
            <th>更新时间</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="product in products" :key="product.id">
            <td>{{ product.id }}</td>
            <td>{{ product.name }}</td>
            <td>{{ product.description }}</td>
            <td>{{ product.unitPrice }}</td>
            <td>{{ product.stockQuantity }}</td>
            <td>{{ product.createTime }}</td>
            <td>{{ product.updateTime }}</td>
            <td class="action-buttons">
              <button class="edit-btn" @click="goToEdit(product.id)">编辑</button>
              <button class="delete-btn" @click="deleteProduct(product.id)">删除</button>
            </td>
          </tr>
        </tbody>
      </table>
      
      <!-- 空数据提示 -->
      <div v-if="!loading && products.length === 0" class="empty">
        没有找到商品数据
      </div>
    </div>

    <!-- 分页 -->
    <div class="pagination" v-if="products.length > 0">
      <div>共 {{ pagination.total }} 条记录</div>
      <div class="page-controls">
        <button 
          :disabled="pagination.currentPage === 1" 
          @click="handlePageChange(pagination.currentPage - 1)"
        >
          上一页
        </button>
        <span>{{ pagination.currentPage }} / {{ Math.ceil(pagination.total / pagination.pageSize) }}</span>
        <button 
          :disabled="pagination.currentPage >= Math.ceil(pagination.total / pagination.pageSize)" 
          @click="handlePageChange(pagination.currentPage + 1)"
        >
          下一页
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.simple-product-list {
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

.primary-btn {
  background-color: #409eff;
  color: white;
  border: 1px solid #409eff;
}

.primary-btn:hover {
  background-color: #66b1ff;
}

.search-section,
.product-table-section {
  background-color: #f9f9f9;
  padding: 20px;
  border-radius: 8px;
  margin-bottom: 20px;
}

.search-section h3,
.product-table-section h3 {
  margin-top: 0;
  margin-bottom: 15px;
  font-size: 18px;
  color: #333;
}

.search-form {
  display: flex;
  gap: 15px;
  align-items: end;
  flex-wrap: wrap;
}

.form-item {
  display: flex;
  flex-direction: column;
  gap: 5px;
  min-width: 200px;
}

.form-item label {
  font-weight: bold;
  font-size: 14px;
  color: #555;
}

.form-item input {
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 14px;
}

.form-actions {
  display: flex;
  gap: 10px;
}

.product-table {
  width: 100%;
  border-collapse: collapse;
  background-color: white;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.product-table th,
.product-table td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #eee;
}

.product-table th {
  background-color: #f0f2f5;
  font-weight: bold;
  color: #333;
}

.product-table tr:hover {
  background-color: #f5f7fa;
}

.action-buttons {
  display: flex;
  gap: 5px;
}

.edit-btn {
  background-color: #67c23a;
  color: white;
  border: 1px solid #67c23a;
  padding: 4px 8px;
  font-size: 12px;
  margin-right: 5px;
}

.edit-btn:hover {
  background-color: #85ce61;
}

.delete-btn {
  background-color: #f56c6c;
  color: white;
  border: 1px solid #f56c6c;
  padding: 4px 8px;
  font-size: 12px;
}

.delete-btn:hover {
  background-color: #f78989;
}

.loading {
  text-align: center;
  padding: 20px;
  color: #666;
}

.empty {
  text-align: center;
  padding: 20px;
  color: #999;
  background-color: white;
  border: 1px dashed #ddd;
  border-radius: 4px;
}

.pagination {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 0;
}

.page-controls {
  display: flex;
  gap: 10px;
  align-items: center;
}
</style>