export interface ProjectMemberImageUri{
  projectMemberImageUri?: string;
}

export interface ProjectRes {
  projectId: number;
  projectImageUri?: string;
  projectName: string;
  projectMemberImageUris?: ProjectMemberImageUri[];
}
