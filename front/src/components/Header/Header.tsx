import * as styles from '@/components/Header/header.css'
import Image from 'next/image'
import { faker } from '@faker-js/faker'
import voicechat from '@/../public/assets/icons/voicechat.svg'
import Notification from '@/../public/assets/icons/notification'
import Logo from '@/components/Logo/Logo'
import vars from '@/styles/variables.css'

const projectName = '스타트 프로젝트, 스프'

type Props={
  theme: 'white'|'black';
  isVoice: boolean;
}
export default function Header({theme,isVoice}:Props) {
  return (
    <div className={`${theme==='white' ? styles.whiteTheme : ""} ${styles.container}`}>
      <div>
        <Logo logoColor={theme==='white' ? 'black' : vars.color.white} leafColor={theme==='white' ? 'black' : vars.color.lightGreen} />
        <p>{projectName}</p>
      </div>
      <div className={styles.assistant}>
        {isVoice && <Image src={voicechat} width={44} height={44} alt="음성 채팅" />}
        <Notification color={theme ==='white' ? 'black' : 'white'}/>
        <img src={faker.image.avatar()} width={44} height={44} alt="프로필" />
      </div>
    </div>
  )
}
