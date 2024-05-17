import Logo from '@/components/Logo/Logo'
import LoginButton from '@/containers/login/LoginButton'
import * as styles from '@/containers/main/styles/mainPage.css'

function MainPageHeader() {
  return (
    <div className={styles.mainPageHeader}>
      {/* <Blacklogo /> */}
      <Logo leafColor="black" logoColor="black" width="110" height="70" />
      <LoginButton />
    </div>
  )
}

export default MainPageHeader
