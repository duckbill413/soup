export type BuildFile = {
  name: string
  package: string
  data: string
}

export type BuildFileSystem = {
  api: BuildFile
  application: BuildFile
  dao: BuildFile
  dto: BuildDto
  entity: BuildFile
}

export type BuildDto = {
  request: Array<BuildFile>
  response: Array<BuildFile>
}
