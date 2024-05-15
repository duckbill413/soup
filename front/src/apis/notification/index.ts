import baseAxios from '../baseAxios'

export async function getNotisAPI() {
  const res = await baseAxios.get(`/notis`)
  return res.data
}

export async function readNotisAPI(notiId: string) {
  const res = await baseAxios.post(`/notis?notiId=${notiId}`)
  return res.data
}
