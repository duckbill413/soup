import { LiveList, LiveObject } from '@liveblocks/client'

export interface PlanData {
  background : string[];
  intro : string[];
  target: string[];
  result : string[];
}

export type ProjectTags = {
  id: string;
  content: string;
}

export type PlanStorage = {
  before : LiveObject<{
    project_background: LiveList<LiveObject<ProjectTags>>,
    project_intro: LiveList<LiveObject<ProjectTags>>,
    project_target: LiveList<LiveObject<ProjectTags>>,
    project_effect: LiveList<LiveObject<ProjectTags>>,
    project_using: boolean
  }>,
  after: LiveObject<{
    project_background:string,
    project_intro:string,
    project_target:string,
    project_effect:string
  }>
}
