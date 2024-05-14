import {
  getAccessToken,
  getRefreshToken,
  tokenClear,
  tokenRefresh,
} from '@/utils/token'
import axios from 'axios'

const TOKEN = getAccessToken()

const baseAxios = axios.create({
  baseURL: process.env.NEXT_PUBLIC_BACKEND_BASE_URL,
  headers: {
    'Content-Type': 'application/json;charset=utf-8',
    Authorization: `Bearer ${TOKEN}`,
  },
})

baseAxios.interceptors.response.use(
  (res) => res,
  async (err) => {
    if (err.response?.status !== 401) return Promise.reject(err)
    const refreshToken = getRefreshToken()
    if (!refreshToken) {
      tokenClear()
      window.location.href = '/main'
    }

    try {
      await tokenRefresh()
    } catch {
      window.location.href = '/main'
    }
    const accessToken = getAccessToken()
    const newConfig = err.config
    newConfig.headers = {
      'Content-Type': 'application/json;charset=utf-8',
      Authorization: `Bearer ${accessToken}`,
    }

    const response = await axios.request(newConfig)
    return response
  },
)

export default baseAxios
