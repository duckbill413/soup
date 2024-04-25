export type APITableRow = {
  id: number
  domain: string
  domainColor: string
  name: string
  method: string
  uri: string
  desc: string
}

export type APITableData = {
  data: Array<APITableRow>
}
