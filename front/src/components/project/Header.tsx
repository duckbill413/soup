import * as styles from '@/styles/project/header.css'
import Logo from '@/../public/assets/icons/logo'
import vars from '@/styles/variables.css'

const projectName = '스타트 프로젝트, 스프';
export default function Header(){
  return(
  <div className={styles.container}>
    <Logo logoColor="white" leafColor={vars.color.lightGreen}/>
    <p>{projectName}</p>
  </div>);
}