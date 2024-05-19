import baseAxios from '../baseAxios'

export async function getDomainNamesAPI(projectId: string) {
  const res = await baseAxios.get(
    `/projects/${projectId}/api-docs/domain/names`,
  )
  return res.data
}
