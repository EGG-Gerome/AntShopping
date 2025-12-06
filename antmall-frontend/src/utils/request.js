import axios from 'axios'
import { ElMessage, ElLoading } from 'element-plus'

// 创建axios实例
const service = axios.create({
  // baseURL: '/api', // 使用代理
  baseURL: process.env.VITE_API_BASE_URL, // 直接使用后端地址
  timeout: 10000 // 请求超时时间
})

// 请求拦截器
service.interceptors.request.use(
  config => {
    // 可以在这里添加token等认证信息
    return config
  },
  error => {
    // 请求错误处理
    console.error('请求错误:', error)
    return Promise.reject(error)
  }
)

// 响应拦截器
service.interceptors.response.use(
  response => {
    const res = response.data
    
    // 统一处理响应
    if (res.success) {
      return res
    } else {
      // 错误信息提示
      ElMessage.error(res.message || '请求失败')
      return Promise.reject(new Error(res.message || '请求失败'))
    }
  },
  error => {
    console.error('响应错误:', error)
    ElMessage.error(error.message || '网络错误')
    return Promise.reject(error)
  }
)

export default service
