import { Dependency } from '@/types/dependency'
import { LiveList, LiveObject } from '@liveblocks/client'

export type ProjectMetadata = {
  group: string
  artifact: string
  name: string
  description: string
  packageName: string
}

export type BuildStorage = {
  project: string
  language: string
  springVersion: string
  metadata: LiveObject<ProjectMetadata>
  packaging: string
  javaVersion: string
  dependencies: LiveList<Dependency>
}
