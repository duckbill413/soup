import baseAxios from '@/apis/baseAxios'
import { PlanData } from '@/containers/plan/types/planStorage'

// 에러 처리
const handleApiError = (message:any, error:any) => {
  console.error(`${message}: `, error);
  throw new Error(message);
};

const makeAIPlan = async (projectId:string, data:PlanData) => {
  try {
    const response = await baseAxios.post(`/projects/${projectId}/plan/ai`, data)
    return response.data
  } catch (error) {
    return handleApiError('기획서를 생성하는 중 오류 발생 : ', error)
  }
}

export default makeAIPlan