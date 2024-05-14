'use client'

import NotificationIcon from '@/../public/assets/icons/notification'
import * as styles from '@/containers/notifications.css'
import vars from '@/styles/variables.css'
import { faker } from '@faker-js/faker'
import Image from 'next/image'
import { useState } from 'react'

type Props = { theme: 'white' | 'black' }

// TODO: sampleData 삭제
const sampleData = [
  {
    id: 1,
    title: '정우현님이 나를 언급했습니다.',
    date: '04-01 22:21',
    content:
      '@최지우 지우야 여기 기획서 키워드에 편리함이 빠진 것 같은데 다시 확인해줄 수 있어?',
  },
  {
    id: 2,
    title: '정우현님이 나를 언급했습니다.',
    date: '04-01 22:21',
    content:
      '@최지우 지우야 여기 기획서 키워드에 편리함이 빠진 것 같은데 다시 확인해줄 수 있어?',
  },
]

function Card(item: any) {
  const { id, title, date, content } = item
  return (
    <div className={styles.notification} key={id}>
      <Image
        unoptimized
        src={faker.image.avatar()}
        width={44}
        height={44}
        alt="프로필"
        className={styles.profile}
      />
      <div className={styles.contents}>
        <div className={styles.notiTop}>
          <span className={styles.notiTitle}>{title}</span>
          <span className={styles.date}>{date}</span>
        </div>
        <p>{content}</p>
      </div>
    </div>
  )
}

export default function Notifications({ theme }: Props) {
  const [open, setOpen] = useState<boolean>(false)

  const handleMenu = () => {
    if (open) {
      setOpen(false)
    } else {
      setOpen(true)
    }
  }

  return (
    <div className={styles.button}>
      <div
        onClick={handleMenu}
        onKeyDown={handleMenu}
        role="presentation"
        className={styles.icon}
      >
        <NotificationIcon
          color={(() => {
            if (open) return `${vars.color.lightGreen}`
            if (theme === 'white') return 'black'
            return 'white'
          })()}
        />
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
                읽지 않은 알림이 <span className={styles.green}>N</span>개
                있습니다.
              </span>
            </div>
            <div className={styles.list}>
              {sampleData.map((item) => Card(item))}
            </div>
          </div>
        </div>
      )}
    </div>
  )
}
