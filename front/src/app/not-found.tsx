'use client'

import Lottie from 'react-lottie-player'
import * as styles from '@/styles/exception.css'
import lottieJson from '@/../public/assets/animations/notFoundAnimation.json'

export default function NotFound() {
  return (
    <div className={styles.container}>
      <Lottie
        loop
        play
        animationData={lottieJson}
        className={styles.animation}
      />
      <h3 className={styles.waitMsg}>
        {
          '원하시는 페이지를 찾을 수 없습니다.\n입력한 주소가 올바른지 확인해주세요.'
        }
      </h3>
    </div>
  )
}
