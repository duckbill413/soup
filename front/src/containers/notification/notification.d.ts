export type Notification = {
  notiId: string
  title: string
  content: string
  notiPhotoUrl: string
  projectId: string
  chatMessageId: string
  createdTime: string
  read: boolean
}

export type NotiEvent = {
  unreadNotiNum: number
  newlyAddedNoti: Notification
}
