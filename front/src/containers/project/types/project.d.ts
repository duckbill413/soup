export interface ProjectMemberImageUri{
  projectMemberImageUrl?: string;
}

export interface ProjectRes {
  id: string;
  name?: string;
  imgUrl: string;
  projectMemberImageUrls:ProjectMemberImageUrl[],
}
