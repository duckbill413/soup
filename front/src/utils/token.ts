import { ACCESS_TOKEN, REFRESH_TOKEN } from '@/constants/token'
import axios from 'axios'
import { destroyCookie, parseCookies } from 'nookies'

const test = () => {
  const cookies = parseCookies().accessToken
  console.log(cookies)
}

const getAccessToken = () => parseCookies().accessToken

const getRefreshToken = () => parseCookies().refreshToken

const setToken = (accessToken: any, refreshToken: any) => {
  window.location.href = `/oauth/kakao/redirect?access-token=${accessToken}&refresh-token=${refreshToken}`
}

const tokenRefresh = async () => {
  console.log('token refresh 중')
  const token = getRefreshToken()
  const instance = axios.create({
    baseURL: process.env.NEXT_PUBLIC_BACKEND_BASE_URL,
  })

  const res = await instance.post('/auth/token/refresh', {
    refreshToken: token,
  })
  const data = res.data()
  setToken(data.accessToken, data.refreshToken)
}

const tokenClear = () => {
  destroyCookie(null, ACCESS_TOKEN)
  destroyCookie(null, REFRESH_TOKEN)
}

export {
  getAccessToken,
  getRefreshToken,
  setToken,
  test,
  tokenClear,
  tokenRefresh,
}
