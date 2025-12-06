import { createRouter, createWebHistory } from 'vue-router'

// 路由配置
const routes = [
  {
    path: '/',
    redirect: '/product/list'
  },
  {
    path: '/simple-test',
    name: 'SimpleTest',
    component: () => import('../views/test/SimpleTestPage.vue')
  },
  {
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
