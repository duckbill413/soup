import baseAxios from '@/apis/baseAxios'
import { InviteMember, JiraRegister } from '@/containers/outline/types/outlineAPI'
import axios from 'axios'

// 에러 처리
const handleApiError = (message:any, error:any) => {
  console.error(`${message}: `, error);
  throw new Error(message);
};
// 사진용 헤더
const photoHeader = {
  headers: {
    'Content-Type': 'multipart/form-data'
  },
};


// 아래 수정해야한다.
export const getOutlineInfoAPI = async(projectId:string) => {
  try{
    const response = await baseAxios.get(`projects/${projectId}/info`)
    return response.data
  } catch(error) {
    return handleApiError('개요 API를 가져오는 중 오류 발생: ', error)
  }
}

export const changePhotoAPI = async (data:FormData) => {
  try {
    const response = await baseAxios.post(`/files/upload`,data, photoHeader)
    return response.data
  } catch (error) {
    return handleApiError('사진을 업로드하는 중 오류 발생: ', error)
  }
}

export const inviteMemberAPI = async(projectId:string, data:InviteMember) => {
  try {
    const response = await baseAxios.post(`/projects/${projectId}/teams`,data)
    return response.data
  } catch (error) {
    if (axios.isAxiosError(error)) {
      if (error.response && error.response.data && error.response.data.message) {
        return handleApiError(`팀원을 초대하는 중 오류 발생 `, error.response.data.message)
      }
    }
    return handleApiError('팀원을 초대하는 중 오류 발생: ', error)
  }
}

export const addJiraAPI = async (projectId:string, data:JiraRegister) => {
  try {
    const response = await baseAxios.put(`/projects/${projectId}/info/jira`, data)
    return response.data
  } catch (error) {
    return handleApiError('JIRA를 등록하는 중 오류 발생: ', error)
  }
}

export const sendOutlineAPI = async (projectId:string) => {
  try {
    const response = await baseAxios.post(`/projects/${projectId}/info/live`)
    return response.data
  } catch (error) {
    return handleApiError('프로젝트 개요 정보를 수정하는 중 오류 발생: ', error)
  }
}