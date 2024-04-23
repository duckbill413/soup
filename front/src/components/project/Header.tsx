import * as styles from '@/styles/project/header.css'
import Image from 'next/image'
import { faker } from '@faker-js/faker';
import voicechat from '@/../public/assets/icons/voicechat.svg'
import notification from '@/../public/assets/icons/notification.svg'
import Logo from '@/components/Logo'
import vars from '@/styles/variables.css'

const projectName = '스타트 프로젝트, 스프'
export default function Header() {
  return (<div className={styles.container}>
    <div>
      <Logo logoColor={vars.color.white} leafColor={vars.color.lightGreen}/>
      <p>{projectName}</p>
    </div>
    <div className={styles.assistant}>
      <Image src={voicechat} width={44} height={44} alt="" />
      <Image src={notification} width={44} height={44} alt="" />
      <img src={faker.image.avatar()} alt="profile-image"/>
    </div>
  </div>)
}