import { LiveList } from '@liveblocks/client'

export type BuildStorage = {
  type: string
  language: string
  languageVersion: string
  version: string
  packaging: string
  group: string
  artifact: string
  name: string
  description: string
  packageName: string
  dependencies: LiveList<number>
}
