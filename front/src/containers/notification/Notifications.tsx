'use client'

import * as styles from '@/containers/notification/notifications.css'
import vars from '@/styles/variables.css'
import Badge from '@mui/material/Badge'
import NotificationsIcon from '@mui/icons-material/Notifications'
import Image from 'next/image'
import { useEffect, useState } from 'react'
import { EventSourcePolyfill, NativeEventSource } from 'event-source-polyfill'
import { getAccessToken } from '@/utils/token'
import { getNotisAPI, readNotisAPI } from '@/apis/notification'
import { NotiEvent, Notification } from './notification'
import { usePathname, useRouter } from 'next/navigation'
import { AppRouterInstance } from 'next/dist/shared/lib/app-router-context.shared-runtime'

type Props = { theme: 'white' | 'black' }

let router: AppRouterInstance
let path: string

function Card(item: Notification) {
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
    if (path === '/') {
      router.push(`/project/${projectId}/outline`)
    }
    // 프로젝트 안에서 읽었을 때는 채팅 id로 이동
    else {
      router.push('/project/66430391e5d4871d5f41bd35/api')
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
          <span className={styles.date}>{createdTime}</span>
        </div>
        <p>{content}</p>
      </div>
    </div>
  )
}

export default function Notifications({ theme }: Props) {
  const [open, setOpen] = useState<boolean>(false)
  const [unreadCnt, setUnreadCnt] = useState<number>(0)
  const [notis, setNotis] = useState<Array<Notification>>([])

  router = useRouter()
  path = usePathname()

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

    eventSource.addEventListener('mention', (event: any) => {
      const obj: NotiEvent = JSON.parse(event.data)
      const unreadNotiNum = obj.unreadNotiNum
      const newNoti: Notification = obj.newlyAddedNoti

      setUnreadCnt(unreadNotiNum)
      setNotis((prev) => [newNoti, ...prev])
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
                읽지 않은 알림이{' '}
                <span className={styles.green}>{unreadCnt}</span>개 있습니다.
              </span>
            </div>
            <div className={styles.list}>{notis.map((item) => Card(item))}</div>
          </div>
        </div>
      )}
    </div>
  )
}
