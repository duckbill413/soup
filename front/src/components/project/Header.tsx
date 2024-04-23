import * as styles from '@/styles/project/header.css'
import Image from 'next/image'
import logo_white from '#/assets/icons/logo_white.svg'

export default function Header(){
  return(
  <div className={styles.container}>
    <Image src={logo_white} alt="" width={60} height={60}/>
    <p>스타트 프로젝트, 스프</p>
  </div>);
}