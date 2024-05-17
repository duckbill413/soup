import { ACCESS_TOKEN, REFRESH_TOKEN } from '@/constants/token'
import axios from 'axios'
import { destroyCookie, parseCookies, setCookie } from 'nookies'

const getAccessToken = () => parseCookies({ path: '/' }).accessToken

const getRefreshToken = () => parseCookies({ path: '/' }).refreshToken

const setToken = (accessToken: any, refreshToken: any) => {
  setCookie(null, 'accessToken', accessToken, { path: '/' })
  setCookie(null, 'refreshToken', refreshToken, { path: '/' })
}

const tokenClear = () => {
  destroyCookie(null, ACCESS_TOKEN, { path: '/' })
  destroyCookie(null, REFRESH_TOKEN, { path: '/' })
}

const tokenRefresh = async () => {
  const token = getRefreshToken()
  const instance = axios.create({
    baseURL: process.env.NEXT_PUBLIC_BACKEND_BASE_URL,
  })

  try {
    const res = await instance.post('/auth/token/refresh', {
      refreshToken: token,
    })
    const data = res.data.result
    setToken(data.accessToken, data.refreshToken)
  } catch (e) {
    console.log('error==', e)
    tokenClear()
    // window.location.href = '/'
  }
}

export { getAccessToken, getRefreshToken, setToken, tokenClear, tokenRefresh }
