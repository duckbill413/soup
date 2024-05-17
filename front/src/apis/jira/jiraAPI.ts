import baseAxios from '@/apis/baseAxios'
import {JiraInfoRes, JiraMembersRes} from "@/containers/func/types/jira";

const handleApiError = (message:any, error:any) => {
    console.error(`${message}: `, error);
    throw new Error(message);
};
export const getJiraInfo = async (projectId:string): Promise<JiraInfoRes> => {
    try {
        const response = await baseAxios.get(`/projects/${projectId}/info/jira`)
        return response.data.result;
    } catch (error) {
        return handleApiError('지라 인포 불러오기 실패 : ', error)
    }
}



export const getJiraMembers = async (projectId:string): Promise<JiraMembersRes[]> => {
    try {
        const response = await baseAxios.get(`/projects/${projectId}/jira`)
        return response.data.result;
    } catch (error) {
        return handleApiError('지라 멤버 불러오기 실패 : ', error)
    }
}


export const synchronizationJira = async (projectId:string) => {
    try {
        const response = await baseAxios.post(`/projects/${projectId}/jira`)
        return response.data;
    } catch (error) {
        return handleApiError('지라 멤버 불러오기 실패 : ', error)
    }
}

