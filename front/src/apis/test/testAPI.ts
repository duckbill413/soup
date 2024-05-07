import baseAxios from '../baseAxios'

export async function test1() {
  const res = await baseAxios.get('/projects?page=0&size=100')
  return res.data
}

export function test2() {}
