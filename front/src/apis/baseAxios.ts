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

let isTokenRefreshing = false
let refreshSubscribers: Array<Function> = []

const onTokenRefreshed = (accessToken: string) => {
  refreshSubscribers.map((callback) => callback(accessToken))
  refreshSubscribers = []
}

const addRefreshSubscriber = (callback: Function) => {
  refreshSubscribers.push(callback)
}

baseAxios.interceptors.response.use(
  (res) => res,
  async (err) => {
    if (err.response?.status !== 401) return Promise.reject(err)
    const newConfig = err.config
    if (!isTokenRefreshing) {
      isTokenRefreshing = true
      const refreshToken = getRefreshToken()
      if (!refreshToken) {
        tokenClear()
        window.location.href = '/main'
      }

      await tokenRefresh()
      const accessToken = getAccessToken()
      isTokenRefreshing = false
      newConfig.headers = {
        'Content-Type': 'application/json;charset=utf-8',
        Authorization: `Bearer ${accessToken}`,
      }
      onTokenRefreshed(accessToken)

      const response = await axios.request(newConfig)
      return response
    }
    const retryOriginalRequest = new Promise((resolve) => {
      addRefreshSubscriber((accessToken: string) => {
        newConfig.headers.Authorization = `Bearer ${accessToken}`
        resolve(axios(newConfig))
      })
    })
    return retryOriginalRequest
  },
)

export default baseAxios
