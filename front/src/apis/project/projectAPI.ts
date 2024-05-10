import baseAxios from '@/apis/baseAxios'
import { ProjectRes } from '@/types/project'

const handleApiError = (message:any, error:any) => {
    console.error(`${message}: `, error);
    throw new Error(message);
};

export const getProjectList = async (): Promise<ProjectRes[]> => {
    try {
        const response = await baseAxios.get(`/projects?page=0&size=100`)
        return response.data.result.content;
    } catch (error) {
        return handleApiError('프로젝트 리스트 에러: ', error);
    }
}
export const createProject = async (): Promise<string> => {
    try {
        const response = await baseAxios.post(`/projects`)
        return response.data.result
    } catch (error) {
        return handleApiError('프로젝트 생성 에러 : ', error)
    }
}

