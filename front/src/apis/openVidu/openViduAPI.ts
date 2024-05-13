import baseAxios from '@/apis/baseAxios'
import axios from "axios";
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

export const getSession = async (projectId: string): Promise<string> => {
    try {
        const response = await baseAxios.get(`/openvidu/search/${projectId}`)
        return response.data.result;
    } catch (error) {
        if (axios.isAxiosError(error)) {
            if (error.response && error.response.status === 404) {
                // 프로젝트Id를 넣었을때 존재하지 않으면 404에러 반환
                // 404에러가 나오면 createSession으로 가고 sessionId 반환
                return await createSession(projectId);
            } 
                return handleApiError('세션을 못갖고오는 에러: ', error);
            
        } 
            return handleApiError('알 수 없는 에러 발생: ', error);
        
    }
}


export const getToken = async (projectId: string): Promise<OpenViduRes> => {
    try {
        const sessionId = await getSession(projectId);

        const response = await baseAxios.get(`/openvidu/${projectId}/${sessionId}/token`)
        return response.data.result;
    } catch (error) {
        return handleApiError('토큰을 못갖는 에러: ', error);
    }
}

