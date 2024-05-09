'use client'

import * as styles from '@/components/IconButton/index.css'
import { IconButtonProps } from '@/types/button'
import { useRouter } from 'next/navigation'

export default function IconButton({
  name,
  children,
  eventHandler,
}: IconButtonProps) {
  const router = useRouter()

  const clickHandler = () => {
    if (typeof eventHandler === 'function') {
      eventHandler()
    } else {
      router.push(eventHandler)
    }
  }

  return (
    <button type="button" className={styles.iconButton} onClick={clickHandler}>
      <span className={styles.buttonName}>{name}</span>
      {children}
    </button>
  )
}
