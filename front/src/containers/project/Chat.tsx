'use client'

import chatSvg from '#/assets/icons/chat/chat.svg'
import chatXSvg from '#/assets/icons/chat/chatX.svg'
import Image from 'next/image'
import styles from '@/containers/project/chat.css'
import {useEffect, useState} from 'react'
import ChatContent from '@/containers/project/chatModal/ChatCotent'
import {getProjectMembers} from "@/apis/member/memberAPI";
import useMemberStore from "@/stores/useMemberStore";

type Props = {
    projectId:string,
}

const SVG_SIZE = 90

export default function Chat({projectId}:Props) {
  // Modal을 보여주는 상태관리 null타입은 첫 상태이고 이 후 true false가 된다.
  const [isVisible, setIsVisible] = useState<boolean | null>(null)
  const {setMembers} = useMemberStore();
  const handleVisible = () => {
    if (isVisible === null || isVisible === false) setIsVisible(true)
    else if (isVisible === true) setIsVisible(false)
  }

    useEffect(() => {
        getProjectMembers(projectId).then(data=>{setMembers(data);})
    }, [projectId]);
  return (
    <>
      <ChatContent isVisible={isVisible} />
      <button type='button' onClick={handleVisible}>
        {!isVisible ? (
          <Image
            className={styles}
            src={chatSvg}
            alt="Chat"
            width={SVG_SIZE}
            height={SVG_SIZE}

          />
        ) : (
          <Image
            className={styles}
            src={chatXSvg}
            alt="Chat"
            width={SVG_SIZE}
            height={SVG_SIZE}
          />
        )}
      </button>
    </>
  )
}
