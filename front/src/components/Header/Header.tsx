'use client'

import voicechat from '@/../public/assets/icons/voicechat.svg'
import * as styles from '@/components/Header/header.css'
import Logo from '@/components/Logo/Logo'
import Notifications from '@/containers/Notifications'
import vars from '@/styles/variables.css'
import Image from 'next/image'
import useMemberStore from "@/stores/useMemberStore";
import defaultImage from "#/assets/images/defaultProfile.png";

const projectName = '스타트 프로젝트, 스프'

type Props = {
  theme: 'white' | 'black'
  useVoice: boolean
}
export default function Header({ theme, useVoice }: Props) {
  const {me} = useMemberStore();
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
        {useVoice && (
          <Image src={voicechat} width={44} height={44} alt="음성 채팅" />
        )}
        <Notifications theme={theme} />
        <Image
          unoptimized
          src={me?.profileImageUrl ? me?.profileImageUrl : defaultImage}
          width={44}
          height={44}
          alt="프로필"
        />
      </div>
    </div>
  )
}
