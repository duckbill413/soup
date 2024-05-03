'use client'

import sample from '#/assets/icons/mainpage/sample1.jpg'
import * as styles from '@/containers/outline/styles/intro/outlineIntro.css'
import { useEffect } from 'react'
// import { getOutlineInfoAPI } from '@/apis/outline/outlineAPI'

function OutlineIntro() {
  const sampleSrc = sample.src

  useEffect(() => {
    // const data = getOutlineInfoAPI('663345425249cc4b837d65ad')
    // console.log(data)
  }, [])
  return (
    <div className={styles.container}>
      <div className={styles.photoDivision}>
        <p>사진</p>
        <img
          src={sampleSrc}
          alt="Project"
          width={355}
          height={258}
          className={styles.img}
        />
      </div>
      <div className={styles.introDivision}>
        <p>프로젝트 이름</p>
        <input placeholder="프로젝트 이름" className={styles.input} />
        <p>프로젝트 설명</p>
        <textarea placeholder="프로젝트 설명" className={styles.textarea} />
      </div>
    </div>
  )
}

export default OutlineIntro
