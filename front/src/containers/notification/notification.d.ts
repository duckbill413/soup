export type Notification = {
  notiId: string
  title: string
  content: string
  notiPhotoUrl: string
  projectId: string
  projectName: string
  chatMessageId: string
  createdTime: string
  isRead: boolean
}

export type NotiEvent = {
  unreadNotiNum: number
  newlyAddedNoti: Notification
}
