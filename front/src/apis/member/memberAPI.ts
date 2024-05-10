import baseAxios from '../baseAxios'

export async function getMemberIdToken() {
  const res = await baseAxios.get('/members/liveblocks')
  return res.data.result
}

export async function getMemberInfo() {
  const res = await baseAxios.get('/members')
  return res.data
}
