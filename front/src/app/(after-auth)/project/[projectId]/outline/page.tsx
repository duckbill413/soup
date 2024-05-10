import * as styles from "@/containers/outline/styles/outline.css"
import { StepTitle } from '@/components/StepTitle/StepTitle'
import OutlineIntro from '@/containers/outline/components/intro'
import OutlineCalendar from '@/containers/outline/components/calendar'
import OutlineAddTool from '@/containers/outline/components/addTool'
import OutlineAddTeam from '@/containers/outline/components/addTeam'
import Live from '@/components/cursor/Live'
import Room from '@/app/(after-auth)/project/[projectId]/outline/Room'
import { ToastContainer } from 'react-toastify'
import 'react-toastify/dist/ReactToastify.css';
import OutlineAddJira from '@/containers/outline/components/jira'

function Outline () {

  return (
    <Room>
      <Live>
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
              
              {/* 지라 추가 */}
              <OutlineAddJira/>
            </div>
          </div>
        </div>
        <ToastContainer/>
      </Live>
    </Room>
  )
}

export default Outline