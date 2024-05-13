interface Tool {
  toolName: string;
  toolUrl: string;
}

export interface OutlineUpdate {
  name: string;
  description?: string;
  imgUrl: string;
  startDate?: string;
  endDate?: string;
  tools?: Tool[] | null;
}

export interface JiraRegister {
  host: string;
  projectKey: string;
  username: string;
  key: string;
}

export interface InviteMember {
  email: string;
  roles: string[];
}

export interface TeamMember {
  id: string;
  displayName: string | null;
  email: string;
  nickname: string;
  phone: string | null;
  profileImageUrl: string;
  roles?: string[];
}