export type Versions = Array<{ version: string }>

export type BuildResult = {
  info: { s3Url: string; builtAt: string }
  build: object
}
