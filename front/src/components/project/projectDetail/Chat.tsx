'use client'

import chatSvg from '#/assets/icons/chat.svg'
import chatXSvg from '#/assets/icons/chatX.svg'
import Image from 'next/image'
import * as styles from '@/styles/project/projectDetail/chat.css'
import { useState } from 'react'
import ChatContent from '@/components/project/projectDetail/chatModal/ChatCotent'


const SVG_SIZE = 110


export default function Chat() {
  // Modal을 보여주는 상태관리 null타입은 첫 상태이고 이 후 true false가 된다.
  const [isVisible, setIsVisible] = useState<boolean | null>(null)


  const handleVisible = () => {
    if (isVisible === null || isVisible === false) setIsVisible(true)
    else if (isVisible === true) setIsVisible(false)
  }
  return (<>
    <ChatContent isVisible={isVisible} />
    <div onClick={handleVisible}>
      {!isVisible ? <Image className={styles.chat} src={chatSvg} alt="Chat" width={SVG_SIZE} height={SVG_SIZE} /> :
        <Image className={styles.chat} src={chatXSvg} alt="Chat" width={SVG_SIZE} height={SVG_SIZE} />}
    </div>
  </>)
}