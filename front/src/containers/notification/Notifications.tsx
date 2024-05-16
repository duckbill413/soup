'use client'

import { getNotisAPI, readNotisAPI } from '@/apis/notification'
import * as styles from '@/containers/notification/notifications.css'
import { useMessageSocketStore } from '@/stores/useMessageSocketStore'
import vars from '@/styles/variables.css'
import { elapsedTime } from '@/utils/elapsedTime'
import { getAccessToken } from '@/utils/token'
import NotificationsIcon from '@mui/icons-material/Notifications'
import Badge from '@mui/material/Badge'
import { EventSourcePolyfill, NativeEventSource } from 'event-source-polyfill'
import { AppRouterInstance } from 'next/dist/shared/lib/app-router-context.shared-runtime'
import Image from 'next/image'
import { useParams, useRouter } from 'next/navigation'
import { useEffect, useState } from 'react'
import { NotiEvent, Notification } from './notification'

let router: AppRouterInstance

function Card(
  item: Notification,
  handleClose: Function,
  handleChatModal: (chatMessageId: string) => void,
  currProjectId: string,
) {
  const {
    notiId,
    title,
    createdTime,
    content,
    notiPhotoUrl,
    projectId,
    chatMessageId,
    read,
  } = item

  const readNoti = async () => {
    // 알림 읽음 처리
    await readNotisAPI(notiId)
    // 홈에서 읽었을 때는 해당 프로젝트의 outline으로 이동
    if (currProjectId !== projectId) {
      router.push(`/project/${projectId}/outline`)
    }
    // 프로젝트 안에서 읽었을 때는 채팅 메세지로 이동
    else {
      handleClose()
      handleChatModal(chatMessageId)
    }
  }

  return (
    <div
      style={
        read
          ? { backgroundColor: vars.color.gray, color: vars.color.deepGray }
          : { backgroundColor: 'white', color: vars.color.black }
      }
      className={styles.notification}
      key={notiId}
      onClick={readNoti}
      onKeyDown={readNoti}
      role="presentation"
    >
      <Image
        unoptimized
        src={notiPhotoUrl}
        width={44}
        height={44}
        alt="프로필"
        className={styles.profile}
      />
      <div className={styles.contents}>
        <div className={styles.notiTop}>
          <span className={styles.notiTitle}>{title}</span>
          <span className={styles.date}>{elapsedTime(createdTime)}</span>
        </div>
        <p>{content}</p>
      </div>
    </div>
  )
}
type RouteParams = {
  projectId: string
}
export default function Notifications() {
  const { projectId } = useParams<RouteParams>()
  const [open, setOpen] = useState<boolean>(false)
  const [unreadCnt, setUnreadCnt] = useState<number>(0)
  const [notis, setNotis] = useState<Array<Notification>>([])

  const { setIsVisible, setTempChatMessageId, moveChatMessageId, isVisible } =
    useMessageSocketStore()
  router = useRouter()
  const handleChatModal = (chatMessageModal: string) => {
    if (isVisible) {
      moveChatMessageId(chatMessageModal)
      return
    }
    setIsVisible(true)
    setTempChatMessageId(chatMessageModal)
  }
  const handleMenu = () => {
    if (open) {
      setOpen(false)
    } else {
      setOpen(true)
    }
  }

  const connectNoti = () => {
    const accessToken = getAccessToken()
    const EventSource = EventSourcePolyfill || NativeEventSource
    const eventSource = new EventSource(
      'https://back.so-up.store/api/notis/sub',
      {
        headers: {
          Authorization: `Bearer ${accessToken}`,
          Connetction: 'keep-alive',
          Accept: 'text/event-stream',
        },
        heartbeatTimeout: 86400000,
      },
    )

    // sse 연결
    eventSource.addEventListener('sse', (event: any) => {
      const obj: NotiEvent = JSON.parse(event.data)
      const { unreadNotiNum } = obj

      setUnreadCnt(unreadNotiNum)
    })

    // 읽음 이벤트 발생
    eventSource.addEventListener('read-noti', (event: any) => {
      const obj: NotiEvent = JSON.parse(event.data)
      const { unreadNotiNum } = obj

      setUnreadCnt(unreadNotiNum)
    })

    // 언급 이벤트 발생
    eventSource.addEventListener('mention', (event: any) => {
      const obj: NotiEvent = JSON.parse(event.data)
      const { unreadNotiNum, newlyAddedNoti } = obj

      setUnreadCnt(unreadNotiNum)

      const alreadyIn = notis.filter(
        (item) => item.notiId === newlyAddedNoti.notiId,
      )
      if (alreadyIn.length === 0) setNotis((prev) => [newlyAddedNoti, ...prev])
    })
  }

  const getNotis = async () => {
    const res = await getNotisAPI()
    setNotis(res.result.notiList)
  }

  useEffect(() => {
    getNotis()
    connectNoti()
  }, [])

  useEffect(() => {
    if (open) {
      getNotis()
    }
  }, [open])

  return (
    <div className={styles.button}>
      <div
        onClick={handleMenu}
        onKeyDown={handleMenu}
        role="presentation"
        className={styles.icon}
      >
        <Badge
          badgeContent={unreadCnt}
          color="error"
          overlap="circular"
          max={9}
        >
          <NotificationsIcon
            fontSize="large"
            sx={open ? { color: vars.color.lightGreen } : { color: 'white' }}
          />
        </Badge>
      </div>
      {open && (
        <div
          className={styles.backdrop}
          onClick={handleMenu}
          onKeyDown={handleMenu}
          role="presentation"
        >
          <div
            className={styles.container}
            onClick={(e) => e.stopPropagation()}
            onKeyDown={(e) => e.stopPropagation()}
            role="presentation"
          >
            <div className={styles.top}>
              <span className={styles.title}>알림</span>
              <span className={styles.sub}>
                <span>{`읽지 않은 알림이 `}</span>
                <span className={styles.green}>{unreadCnt}</span>개 있습니다.
              </span>
            </div>
            <div className={styles.list}>
              {notis.map((item) =>
                Card(item, handleMenu, handleChatModal, projectId),
              )}
            </div>
          </div>
        </div>
      )}
    </div>
  )
}
