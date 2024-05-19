import baseAxios from '@/apis/baseAxios'

const handleApiError = (message:any, error:any) => {
    console.error(`${message}: `, error);
    throw new Error(message);
};


export const updateERD = async (projectId:string,json:string): Promise<void> => {
    try {
        const response = await baseAxios.put(`/projects/${projectId}/vuerd`,json)
        return response.data;
    } catch (error) {
        return handleApiError('ERD 업데이트 싪해 : ', error)
    }
}

