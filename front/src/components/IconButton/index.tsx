'use client'

import * as styles from '@/components/IconButton/index.css'
import { IconButtonProps } from '@/types/button'
import { useRouter } from 'next/navigation'
import { MouseEvent } from 'react'

export default function IconButton({
  name,
  children,
  eventHandler,
}: IconButtonProps) {
  const router = useRouter()

  const clickHandler = (e: MouseEvent<HTMLButtonElement>) => {
    if (typeof eventHandler === 'function') {
      eventHandler(e)
    } else {
      router.push(eventHandler)
    }
  }

  return (
    <button
      type="button"
      className={styles.iconButton}
      onClick={(e) => clickHandler(e)}
    >
      <span className={styles.buttonName}>{name}</span>
      {children}
    </button>
  )
}
