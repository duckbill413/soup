import { LiveList, LiveObject } from '@liveblocks/client'

export type ProjectTool = {
  id: string;
  name : string;
  url? : string;
}

export type ProjectMember = {
  id: string;
  name: string;
  roles: LiveList<LiveObject<Role>>;
  email: string;
  jira: boolean;
}

export type Role = {
  id: string;
  role_name: string;

}

export type OutlineStorage = {
  project_name: string,
  project_description: string,
  project_photo: string,
  project_startDate: string,
  project_endDate: string,
  project_tools: LiveList<LiveObject<ProjectTool>>,
  project_team: LiveList<LiveObject<ProjectMember>>
}