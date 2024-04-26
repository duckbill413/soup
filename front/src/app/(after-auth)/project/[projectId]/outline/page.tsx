import * as styles from "@/containers/outline/styles/outline.css"
import { StepTitle } from '@/components/StepTitle/StepTitle'
import OutlineIntro from '@/containers/outline/components/intro/Main'
import OutlineCalendar from '@/containers/outline/components/calendar/Main'
import OutlineAddTool from '@/containers/outline/components/addTool/Main'
import OutlineAddTeam from '@/containers/outline/components/addTeam/Main'

function Outline () {
  return (
    <div>
      <StepTitle stepNum={1} title="프로젝트 개요" desc="프로젝트 정보를 입력하고, 팀을 구성해보세요."/>
      <div className={styles.container}>
        {/*  개요 아래 최상위 div  */}
        <div className={styles.mainDivision}>

          {/* 사진, 프로젝트 이름, 설명 영역 */}
          <OutlineIntro/>

          {/* 날짜 설정 영역 */}
          <OutlineCalendar/>

          {/*  협업 툴  */}
          <OutlineAddTool/>

          {/*  팀원 추가  */}
          <OutlineAddTeam/>

        </div>
      </div>
    </div>
  )
}

export default Outline