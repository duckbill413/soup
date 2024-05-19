import {
  FirstPagepic1,
  FirstPagepic2,
  FirstPagepic3,
} from '#/assets/icons/mainpage/intro/firstillust'
import * as styles from '@/containers/main/styles/mainIntro.css'
import DownButton from '#/assets/icons/mainpage/downbutton'
import { bounceAnimation } from '@/containers/main/styles/mainPage.css'

function MainIntro({ onButtonClick }: { onButtonClick?: () => void }) {
  return (
    <div className={styles.container}>
      <div className={styles.mainDiv}>
        <p className={styles.title}>Start Organization, Upgrade Project.</p>
        <p className={styles.subTitle}>
          Spring 프레임워크 기반 프로젝트의 기획, 설계가
        </p>
        <p className={styles.subTitle}>간결해지도록 도와드립니다.</p>

        <div className={styles.subDiv}>
          <FirstPagepic1 />
          <FirstPagepic2 />
          <FirstPagepic3 />
        </div>

        <button
          type="button"
          className={bounceAnimation}
          onClick={onButtonClick}
          aria-label="내려가기"
        >
          <DownButton />
        </button>
      </div>
    </div>
  )
}

export default MainIntro
