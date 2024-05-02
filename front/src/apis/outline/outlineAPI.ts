import baseAxios from '@/apis/baseAxios'

const getOutlineInfoAPI = async(projectId:string)=> {
  try{
    const response = baseAxios.get(`projects/${projectId}/info`)
    return await response
  } catch(error) {
    console.error('Failed to fetch data', error);
    throw error
  }
}

export default getOutlineInfoAPI

