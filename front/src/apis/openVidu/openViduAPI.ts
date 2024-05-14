import baseAxios from '@/apis/baseAxios'
import {OpenViduRes} from "@/containers/project/types/openVidu";

const handleApiError = (message: string, error: unknown) => {
    console.error(`${message}: `, error);
    throw new Error(message);
};

export const createSession = async (projectId: string): Promise<string> => {
    try {
        const response = await baseAxios.post(`/openvidu/create/${projectId}`)
        return response.data.result
    } catch (error) {
        return handleApiError('세션 생성 에러 : ', error)
    }
}



export const getToken = async (projectId: string): Promise<OpenViduRes> => {
    try {
        const sessionId = await createSession(projectId);
        console.log(sessionId);
        const response = await baseAxios.get(`/openvidu/${projectId}/${sessionId}/token`)
        return response.data.result;
    } catch (error) {
        return handleApiError('토큰을 못갖는 에러: ', error);
    }
}

