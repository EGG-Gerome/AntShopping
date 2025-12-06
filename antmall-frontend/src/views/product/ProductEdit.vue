<script setup>
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { useProductStore } from '../../stores/product'

const router = useRouter()
const route = useRoute()
const productStore = useProductStore()

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

// 表单验证规则
const rules = {
  name: [
    { required: true, message: '请输入商品名称', trigger: 'blur' },
    { min: 1, max: 50, message: '商品名称长度在 1 到 50 个字符', trigger: 'blur' }
  ],
  unitPrice: [
    { required: true, message: '请输入商品单价', trigger: 'blur' },
    { type: 'number', min: 0, message: '商品单价不能为负数', trigger: 'blur' }
  ],
  stockQuantity: [
    { required: true, message: '请输入库存数量', trigger: 'blur' },
    { type: 'number', min: 0, message: '库存数量不能为负数', trigger: 'blur' }
  ]
}

// 表单引用
const editFormRef = ref(null)

// 加载状态
const loading = ref(false)

// 获取商品详情
const getProductDetail = async () => {
  // 如果商品列表为空，先获取商品列表
  if (productStore.products.length === 0) {
    await productStore.getProducts(1)
  }
  
  // 从商品列表中查找当前商品
  const product = productStore.products.find(item => item.id === productId.value)
  
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
    ElMessage.error('商品不存在')
    router.push('/product/list')
  }
}

// 页面加载时获取商品详情
onMounted(async () => {
  await getProductDetail()
})

// 提交编辑表单
const submitForm = async () => {
  // 表单验证
  if (!editFormRef.value) return
  
  await editFormRef.value.validate((valid) => {
    if (!valid) {
      return false
    }
  })
  
  try {
    loading.value = true
    await productStore.editProduct(editForm.value)
    ElMessage.success('编辑商品成功')
    // 跳转到商品列表页面
    router.push('/product/list')
  } catch (error) {
    console.error('编辑商品失败:', error)
    ElMessage.error('编辑商品失败')
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
  <div class="product-edit">
    <!-- 页面标题和操作按钮 -->
    <div class="page-header">
      <h2>编辑商品</h2>
      <div class="header-actions">
        <el-button @click="backToList">返回列表</el-button>
      </div>
    </div>

    <!-- 编辑表单 -->
    <div class="card">
      <el-form
        ref="editFormRef"
        :model="editForm"
        :rules="rules"
        label-width="120px"
      >
        <!-- 隐藏的ID字段 -->
        <el-input
          v-model="editForm.id"
          type="hidden"
        ></el-input>
        
        <el-form-item label="商品名称" prop="name">
          <el-input
            v-model="editForm.name"
            placeholder="请输入商品名称"
            maxlength="50"
          ></el-input>
        </el-form-item>
        
        <el-form-item label="商品描述" prop="description">
          <el-input
            v-model="editForm.description"
            placeholder="请输入商品描述"
            type="textarea"
            rows="3"
          ></el-input>
        </el-form-item>
        
        <el-form-item label="商品单价" prop="unitPrice">
          <el-input-number
            v-model="editForm.unitPrice"
            placeholder="请输入商品单价"
            :min="0"
            :step="0.01"
            :precision="2"
          ></el-input-number>
        </el-form-item>
        
        <el-form-item label="库存数量" prop="stockQuantity">
          <el-input-number
            v-model="editForm.stockQuantity"
            placeholder="请输入库存数量"
            :min="0"
            :step="1"
          ></el-input-number>
        </el-form-item>
        
        <el-form-item>
          <div class="form-actions">
            <el-button type="primary" @click="submitForm" :loading="loading">
              保存
            </el-button>
            <el-button @click="resetForm">重置</el-button>
            <el-button @click="backToList">取消</el-button>
          </div>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<style scoped>
.product-edit {
  width: 100%;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1.5rem;
}

.page-header h2 {
  font-size: 1.25rem;
  font-weight: 600;
  color: #303133;
}

.header-actions {
  display: flex;
  gap: 0.5rem;
}

.form-actions {
  display: flex;
  gap: 0.5rem;
  margin-top: 1rem;
}
</style>
