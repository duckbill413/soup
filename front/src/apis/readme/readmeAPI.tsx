import baseAxios from "@/apis/baseAxios";

const handleApiError = (message:any, error:any) => {
    console.error(`${message}: `, error);
    throw new Error(message);
};

export const getReadMeTemplate = async (projectId:string): Promise<string> => {
    try {
        const response = await baseAxios.get(`/readme/init/${projectId}?templateName=Init Template`)
        return response.data.result.content;
    } catch (error) {
        return handleApiError(': ', error);
    }
}