import { ACCESS_TOKEN, REFRESH_TOKEN } from '@/constants/token'
import axios from 'axios'
import { destroyCookie, parseCookies, setCookie } from 'nookies'

const getAccessToken = () => parseCookies().accessToken

const getRefreshToken = () => parseCookies().refreshToken

const setToken = (accessToken: any, refreshToken: any) => {
  setCookie(null, 'accessToken', accessToken)
  setCookie(null, 'refreshToken', refreshToken)
}

const tokenClear = () => {
  destroyCookie(null, ACCESS_TOKEN)
  destroyCookie(null, REFRESH_TOKEN)
}

const tokenRefresh = async () => {
  const token = getRefreshToken()
  const instance = axios.create({
    baseURL: process.env.NEXT_PUBLIC_BACKEND_BASE_URL,
  })

  tokenClear()
  const res = await instance.post('/auth/token/refresh', {
    refreshToken: token,
  })
  const data = res.data.result
  setToken(data.accessToken, data.refreshToken)
}

export { getAccessToken, getRefreshToken, setToken, tokenClear, tokenRefresh }
