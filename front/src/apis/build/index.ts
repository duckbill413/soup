import baseAxios from '../baseAxios'

export async function getSpringBootVersionsAPI() {
  const res = await baseAxios.get(`/spring/versions`)
  return res.data
}

export async function getDependenciesAPI() {
  const res = await baseAxios.get(`/spring/dependencies`)
  return res.data
}
