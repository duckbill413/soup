import { create } from 'zustand';
import { MemberRes } from "@/containers/project/types/member";
import {JiraInfoRes, JiraMembersRes} from "@/containers/func/types/jira";

type Store = {
    isConnected: boolean;
    jiraInfo: JiraInfoRes;
    setJiraInfo: (jiraInfo:JiraInfoRes) => void;
    setIsConnected: (value:boolean) => void;
    jiraMembers: MemberRes[];
    setJiraMembers: (members: MemberRes[]) => void;
};

const useJiraStore = create<Store>((set) => ({
    isConnected: false,
    setIsConnected: (value) => set({isConnected: value}),
    jiraInfo: {
        jiraHost: '',
        jiraProjectKey: '',
        jiraUsername: '',
        jiraKey: ''
    },
    setJiraInfo: (jiraInfo) => set({jiraInfo}),
    jiraMembers: [],
    setJiraMembers: (members) => set({jiraMembers: members}),
}));

export default useJiraStore;


export function convertJiraMembers(jiraMembers: JiraMembersRes[]): MemberRes[] {
    return jiraMembers.map(jiraMember => ({
        id: jiraMember.accountId,
        nickname: jiraMember.displayName,
        profileImageUrl: jiraMember.avatarUrls['48x48'], // 48x48 크기의 이미지 URL 사용
        email: "",
        displayName: "",
        phone: "",
        roles: [],
    }));
}