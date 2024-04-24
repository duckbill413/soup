'use client'

import * as styles from "@/containers/outline/styles/outline.css"
import { StepTitle } from '@/components/StepTitle/StepTitle'
import sample from "#/assets/icons/mainpage/sample1.jpg"
import Calendar from '@/containers/outline/components/calendar'
import Add from "#/assets/icons/outline/addButton.svg"
import Image from 'next/image'

function Outline () {
  const sampleSrc = sample.src
  return (
    <div>
      <StepTitle stepNum={1} title="프로젝트 개요" desc="프로젝트 정보를 입력하고, 팀을 구성해보세요."/>
      <div style={{display:'flex', justifyContent:'center'}}>
        <div style={{ display: 'flex', flexDirection: 'column', width: '93%' }}>
          {/*  개요 아래 최상위 div  */}
          <div style={{ width: '100%', display: 'flex' }}>

            {/* 사진, 프로젝트 이름, 설명 영역 */}
            <div style={{ display: 'flex', flexDirection: 'column' }}>
              <p className={styles.boldText}>사진</p>
              <img src={sampleSrc} alt="Project" width={355} height={258} style={{ borderRadius: '10px' }} />
            </div>
            <div style={{ display: 'flex', flexDirection: 'column' }}>
              <p className={styles.boldText}>프로젝트 이름</p>
              <input />
              <p className={styles.boldText}>프로젝트 설명</p>
              <textarea />
            </div>
          </div>

          {/* 날짜 설정 영역 */}
          <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'flex-start' }}>
            <p className={styles.boldText}>기간</p>
            <div style={{ display: 'flex' }}>
              <Calendar />
              <Calendar />
            </div>
          </div>

          {/*  협업 툴  */}
          <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'flex-start' }}>
            <div style={{display: 'flex', alignItems:'center'}}>
              <p className={styles.boldText}>협업 툴</p>
              <Image src={Add} alt="add" width={32} height={32}/>
            </div>

          </div>


        </div>
      </div>
    </div>
  )
}

export default Outline