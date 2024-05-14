import baseAxios from "@/apis/baseAxios";
import {ChatRes} from "@/containers/project/types/chat";
import axios from "axios";

require('dayjs/locale/ko'); // 한국어 설정

const handleApiError = (message:any, error:any) => {
    console.error(`${message}: `, error);
    throw new Error(message);
};

export const getChatting = async (roomId:string): Promise<ChatRes[]> => {
    try {
        const offset = new Date().getTimezoneOffset() * 60000;
        const today = new Date(Date.now() - offset);
        const response = await baseAxios.get(`/chatrooms/${roomId}?page=0&size=1000&standardTime=${today.toISOString().slice(0,-1)}`)
        const data = response.data.result;
        return data.reverse();
    } catch (error) {
        if (axios.isAxiosError(error)) {
            if (error.response && error.response.status === 500) {
                return [];
            }
            return handleApiError('채팅을 불러올 수 없음: ', error);

        }
        return handleApiError('알 수 없는 에러 발생: ', error);

    }
}
