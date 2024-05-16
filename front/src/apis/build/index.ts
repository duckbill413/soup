import baseAxios from '../baseAxios'

export async function getSpringBootVersionsAPI() {
  const res = await baseAxios.get(`/spring/versions`)
  return res.data
}

export async function getDependenciesAPI() {
  const res = await baseAxios.get(`/spring/dependencies`)
  return res.data
}

export async function getBuildFileAPI(projectId: string) {
  const res = await baseAxios.get(`/projects/${projectId}/builder/structure`)
  return res.data
}

export async function buildProjectAPI(projectId: string) {
  const res = await baseAxios.post(`/projects/${projectId}/builder`)
  return res.data
}
