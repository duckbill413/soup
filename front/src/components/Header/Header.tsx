'use client'

import defaultImage from '#/assets/images/defaultProfile.png'
import unvoicechat from '@/../public/assets/icons/unvoicechat.svg'
import voicechat from '@/../public/assets/icons/voicechat.svg'
import * as styles from '@/components/Header/header.css'
import Logo from '@/components/Logo/Logo'
import Notifications from '@/containers/notification/Notifications'
import useAudioStore from '@/stores/useAudioStore'
import useMemberStore from '@/stores/useMemberStore'
import vars from '@/styles/variables.css'
import { tokenClear } from '@/utils/token'
import { Button, Fade } from '@mui/material'
import Image from 'next/image'
import { useState } from 'react'

const projectName = '스타트 프로젝트, 스프'

type Props = {
  theme: 'white' | 'black'
  useVoice: boolean
}
export default function Header({ theme, useVoice }: Props) {
  const { me } = useMemberStore()
  const { localStreamManager, toggleAudio } = useAudioStore()
  const [open, setOpen] = useState(false)

  const handleMenu = () => {
    if (open) {
      setOpen(false)
    } else {
      setOpen(true)
    }
  }

  return (
    <div
      className={`${theme === 'white' ? styles.whiteTheme : ''} ${styles.container}`}
    >
      <div>
        <Logo
          logoColor={theme === 'white' ? 'black' : vars.color.white}
          leafColor={theme === 'white' ? 'black' : vars.color.lightGreen}
        />
        <p>{projectName}</p>
      </div>
      <div className={styles.assistant}>
        {useVoice &&
          (localStreamManager?.stream.audioActive ? (
            <Image
              onClick={toggleAudio}
              src={voicechat}
              width={44}
              height={44}
              alt="음성 채팅"
            />
          ) : (
            <Image
              onClick={toggleAudio}
              src={unvoicechat}
              width={44}
              height={44}
              alt="음성 채팅"
            />
          ))}
        <Notifications />
        <Image
          unoptimized
          src={me?.profileImageUrl ? me?.profileImageUrl : defaultImage}
          width={44}
          height={44}
          alt="프로필"
          onClick={handleMenu}
        />
        <Fade in={open}>
          <div
            className={styles.backdrop}
            onClick={handleMenu}
            onKeyDown={handleMenu}
            role="presentation"
          >
            <div className={styles.menu}>
              <Image
                unoptimized
                src={me?.profileImageUrl ? me?.profileImageUrl : defaultImage}
                width={44}
                height={44}
                alt="프로필"
                className={styles.profile}
              />
              <span className={styles.nickname}>{me?.nickname}</span>
              <span className={styles.email}>{me?.email}</span>
              <Button
                variant="text"
                onClick={() => {
                  tokenClear()
                  window.location.href = '/'
                }}
              >
                로그아웃
              </Button>
            </div>
          </div>
        </Fade>
      </div>
    </div>
  )
}
