'use client'

import { IconButtonProps } from '@/types/button'
import * as styles from '@/containers/api/iconButton.css'
// import Image from 'next/image'
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
      {/* <Image
        src={`/assets/icons/${icon}.svg`}
        alt="filter"
        width={16}
        height={16}
        // style={{ color: 'inherit' }}
        // className={styles.imgColor}
      /> */}
      {children}
    </button>
  )
}
