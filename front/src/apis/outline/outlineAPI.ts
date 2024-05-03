import baseAxios from '@/apis/baseAxios'

// 에러 처리
const handleApiError = (message:any, error:any) => {
  console.error(`${message}: `, error);
  throw new Error(message);
};

// 아래 수정해야한다.
const getOutlineInfoAPI = async(projectId:string) => {
  try{
    const response = await baseAxios.get(`projects/${projectId}/info`)
    return response.data
  } catch(error) {
    return handleApiError('개요 API를 가져오는 중 오류 발생: ', error)
  }
}

export default getOutlineInfoAPI