import Blacklogo from "#/assets/icons/mainpage/blacklogo"
import * as styles from "@/containers/main/styles/mainPage.css"


function MainPageHeader () {
  return (
    <div className={styles.mainPageHeader}>
      <Blacklogo />
      <button type="button" className={styles.mainPageHeaderBtn}>
        <img
          src="//k.kakaocdn.net/14/dn/btroDszwNrM/I6efHub1SN5KCJqLm1Ovx1/o.jpg"
          width="222"
          alt="카카오 로그인 버튼"
        />
      </button>
    </div>
  )
}

export default MainPageHeader