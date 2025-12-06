<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useProductStore } from '../../stores/product'

const router = useRouter()
const productStore = useProductStore()

// 搜索表单
const searchForm = ref({
  name: '',
  description: ''
})

// 加载状态
const loading = ref(false)

// 删除确认对话框
const deleteDialogVisible = ref(false)
const deleteIds = ref([])

// 页面加载时获取商品列表
onMounted(() => {
  getProducts()
})

// 获取商品列表
const getProducts = async (page = 1) => {
  await productStore.getProducts(page)
}

// 搜索商品
const searchProducts = () => {
  productStore.setSearchParams(searchForm.value)
  getProducts()
}

// 重置搜索
const resetSearch = () => {
  searchForm.value = {
    name: '',
    description: ''
  }
  productStore.resetSearchParams()
  getProducts()
}

// 跳转到添加商品页面
const goToAdd = () => {
  router.push('/product/add')
}

// 跳转到编辑商品页面
const goToEdit = (id) => {
  router.push(`/product/edit/${id}`)
}

// 显示删除确认对话框
const showDeleteConfirm = (id) => {
  deleteIds.value = [id]
  ElMessageBox.confirm('确定要删除该商品吗？', '删除确认', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    deleteProduct()
  }).catch(() => {
    // 取消删除
  })
}

// 删除商品
const deleteProduct = async () => {
  try {
    loading.value = true
    await productStore.deleteProduct(deleteIds.value)
    ElMessage.success('删除成功')
  } catch (error) {
    console.error('删除失败:', error)
  } finally {
    loading.value = false
  }
}

// 分页变化处理
const handlePageChange = (page) => {
  getProducts(page)
}

// 页面大小变化处理
const handleSizeChange = (size) => {
  productStore.pageSize = size
  getProducts(1)
}
</script>

<template>
  <div class="product-list">
    <!-- 页面标题和操作按钮 -->
    <div class="page-header">
      <h2>商品列表</h2>
      <el-button type="primary" @click="goToAdd">添加商品</el-button>
    </div>

    <!-- 搜索表单 -->
    <div class="card">
      <h3>搜索条件</h3>
      <div class="search-form">
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="商品名称">
              <el-input
                v-model="searchForm.name"
                placeholder="请输入商品名称"
                clearable
                @keyup.enter="searchProducts"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="商品描述">
              <el-input
                v-model="searchForm.description"
                placeholder="请输入商品描述"
                clearable
                @keyup.enter="searchProducts"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8" class="form-actions">
            <el-button type="primary" @click="searchProducts">搜索</el-button>
            <el-button @click="resetSearch">重置</el-button>
          </el-col>
        </el-row>
      </div>
    </div>

    <!-- 商品列表 -->
    <div class="card">
      <el-table
        v-loading="productStore.loading"
        :data="productStore.products"
        stripe
        style="width: 100%"
      >
        <el-table-column prop="id" label="商品ID" width="180" />
        <el-table-column prop="name" label="商品名称" />
        <el-table-column prop="description" label="商品描述" />
        <el-table-column prop="unitPrice" label="单价" />
        <el-table-column prop="stockQuantity" label="库存" width="100" />
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column prop="updateTime" label="更新时间" width="180" />
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="scope">
            <el-button
              type="primary"
              size="small"
              @click="goToEdit(scope.row.id)"
            >
              编辑
            </el-button>
            <el-button
              type="danger"
              size="small"
              @click="showDeleteConfirm(scope.row.id)"
            >
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <div class="pagination-container">
        <el-pagination
          v-model:current-page="productStore.currentPage"
          v-model:page-size="productStore.pageSize"
          :page-sizes="[10, 20, 50, 100]"
          :total="productStore.total"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handlePageChange"
        >
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<style scoped>
.product-list {
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

.search-form {
  margin-top: 1rem;
}

.form-actions {
  display: flex;
  gap: 0.5rem;
  align-items: flex-end;
}

.pagination-container {
  margin-top: 1rem;
  display: flex;
  justify-content: flex-end;
}
</style>
