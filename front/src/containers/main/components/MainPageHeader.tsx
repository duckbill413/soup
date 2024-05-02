import Blacklogo from '#/assets/icons/mainpage/blacklogo'
import LoginButton from '@/containers/login/LoginButton'
import * as styles from '@/containers/main/styles/mainPage.css'

function MainPageHeader() {
  return (
    <div className={styles.mainPageHeader}>
      <Blacklogo />
      <LoginButton />
    </div>
  )
}

export default MainPageHeader
