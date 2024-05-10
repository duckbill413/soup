import {MemberRes} from "@/containers/project/types/member";
import baseAxios from "@/apis/baseAxios";

const handleApiError = (message:any, error:any) => {
    console.error(`${message}: `, error);
    throw new Error(message);
};

export const getProjectMembers = async (projectId:string): Promise<MemberRes[]> => {
    try {
        const response = await baseAxios.get(`/projects/${projectId}/teams`)
        return response.data.result;
    } catch (error) {
        return handleApiError('프로젝트 멤버 불러오기 실패: ', error);
    }
}

export async function getMemberIdToken() {
  const res = await baseAxios.get('/members/liveblocks')
  return res.data.result
}

export async function getMemberInfo() {
  const res = await baseAxios.get('/members')
  return res.data
}
