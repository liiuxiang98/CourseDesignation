import axios from 'axios'
import { ElMessage } from 'element-plus'

const request = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || '/api',
  timeout: 15000
})

request.interceptors.response.use(
  (response) => {
    const res = response.data
    if (res && res.code === 1) {
      return res.data
    }
    const message = res?.msg && res.msg !== 'msg' ? res.msg : '操作失败，请稍后重试'
    ElMessage.error(message)
    return Promise.reject(new Error(message))
  },
  (error) => {
    const message = error.response?.data?.msg || '无法连接后端，请确认 Spring Boot 与 MySQL 已启动'
    ElMessage.error(message)
    return Promise.reject(error)
  }
)

export default request
