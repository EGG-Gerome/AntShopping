import { createRouter, createWebHistory } from 'vue-router'

// 路由配置
const routes = [
  {
    path: '/',
    redirect: '/product/list'
  },
  {
    /* 调试信息：
    如果能看到这个页面，说明Vue 3的基础功能正常工作。
    如果只能看到标题，说明路由或组件渲染有问题。
    如果完全看不到内容，说明Vue没有正常加载。
     */
    path: '/simple-test',
    name: 'SimpleTest',
    component: () => import('../views/test/SimpleTestPage.vue')
  },
  {
    // 这是一个简单的测试页面，用于验证路由和组件渲染是否正常工作。
    path: '/test',
    name: 'Test',
    component: () => import('../views/test/TestPage.vue')
  },
  {
    path: '/product/list',
    name: 'ProductList',
    component: () => import('../views/product/SimpleProductList.vue')
  },
  {
    path: '/product/add',
    name: 'ProductAdd',
    component: () => import('../views/product/SimpleProductAdd.vue')
  },
  {
    path: '/product/edit/:id',
    name: 'ProductEdit',
    component: () => import('../views/product/SimpleProductEdit.vue'),
    props: true
  }
]

// 创建路由实例
const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
