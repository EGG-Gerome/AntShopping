import { defineStore } from 'pinia'
import productApi from '../api/product'

// 定义商品状态管理
export const useProductStore = defineStore('product', {
  state: () => ({
    products: [],
    total: 0,
    currentPage: 1,
    pageSize: 20,
    loading: false,
    searchParams: {
      name: '',
      description: ''
    }
  }),

  actions: {
    // 分页查询商品
    async getProducts(page = 1, resetParams = false) {
      this.loading = true
      try {
        const queryBO = {
          currentPage: page,
          pageSize: this.pageSize,
          ...(resetParams ? {} : this.searchParams)
        }
        
        const res = await productApi.page(queryBO)
        this.products = res.data.dataList || []
        this.total = res.data.totalSize || 0
        this.currentPage = page
        return res
      } finally {
        this.loading = false
      }
    },

    // 设置搜索参数
    setSearchParams(params) {
      this.searchParams = { ...this.searchParams, ...params }
    },

    // 重置搜索参数
    resetSearchParams() {
      this.searchParams = {
        name: '',
        description: ''
      }
    },

    // 添加商品
    async addProduct(data) {
      this.loading = true
      try {
        const res = await productApi.add(data)
        // 添加成功后刷新列表
        await this.getProducts(this.currentPage)
        return res
      } finally {
        this.loading = false
      }
    },

    // 编辑商品
    async editProduct(data) {
      this.loading = true
      try {
        const res = await productApi.edit(data)
        // 编辑成功后刷新列表
        await this.getProducts(this.currentPage)
        return res
      } finally {
        this.loading = false
      }
    },

    // 删除商品
    async deleteProduct(ids) {
      this.loading = true
      try {
        const res = await productApi.delete(ids)
        // 删除成功后刷新列表
        await this.getProducts(this.currentPage)
        return res
      } finally {
        this.loading = false
      }
    }
  }
})
