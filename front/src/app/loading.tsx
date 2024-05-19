'use client'

import Lottie from 'react-lottie-player'
import * as styles from '@/styles/exception.css'
import lottieJson from '@/../public/assets/animations/loadingAnimation.json'

export default function Loading() {
  return (
    <div className={styles.container}>
      <Lottie
        loop
        play
        animationData={lottieJson}
        className={styles.animation}
      />
      <h3 className={styles.waitMsg}>잠시만 기다려주세요</h3>
    </div>
  )
}
