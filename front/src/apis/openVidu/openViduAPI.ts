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
        const response = await baseAxios.get(`/openvidu/${projectId}/${sessionId}/token`)
        return response.data.result;
    } catch (error) {
        return handleApiError('토큰을 못갖는 에러: ', error);
    }
}

// export const leaveSession = async (projectId: string,sessionId:string,connectionId:string): Promise<OpenViduRes> => {
//     try {
//         const response = await baseAxios.post(`/openvidu/${projectId}/${sessionId}/${connectionId}/leave`)
//         return response.data.result;
//     } catch (error) {
//         return handleApiError('해당 토큰이 세션퇴장을 실패: ', error);
//     }
// }


