'use client'

import Lottie from 'react-lottie-player'
import * as styles from '@/styles/exception.css'
import lottieJson from '@/../public/assets/animations/errorAnimation.json'

export default function Error() {
  return (
    <div className={styles.container}>
      <Lottie
        loop
        play
        animationData={lottieJson}
        className={styles.animation}
      />
      <h3 className={styles.waitMsg}>
        {'서버와의 연결이 원활하지 않아요.\n잠시 후 다시 시도해주세요.'}
      </h3>
    </div>
  )
}
