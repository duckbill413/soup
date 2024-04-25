'use client'

import * as styles from "@/containers/outline/styles/outline.css"
import { StepTitle } from '@/components/StepTitle/StepTitle'
import sample from "#/assets/icons/mainpage/sample1.jpg"
import StartCalendar from '@/containers/outline/components/startCalendar'
import Add from "#/assets/icons/outline/addButton.svg"
import Image from 'next/image'
import ToolTable from '@/containers/outline/components/toolTable'
import { useState } from 'react'
import TeamTable from "@/containers/outline/components/teamTable"
import vars from "@/styles/variables.css"
import EndCalendar from '@/containers/outline/components/endCalendar'


interface RoleTableType {
  name: string;
  description: string;
}

interface TeamTableType {
  name: string;
  role: string;
  email: string;
}

function Outline () {
  const sampleSrc = sample.src
  const [rows, setRows] = useState<RoleTableType[]>([]);
  const [teamRows, setTeamRows] = useState<TeamTableType[]>([]);

  // AddButton 클릭 이벤트 핸들러
  const addTool = () => {
    const newRow = {
      name: `Tool ${rows.length + 1}`,
      description: 'https://naver.com',
    };
    setRows([...rows, newRow]);
  };

  const addTeam = () => {
    const newRow = {
      name: `Tool ${teamRows.length + 1}`,
      role: 'Team',
      email: 'https://naver.com',
    };
    setTeamRows([...teamRows, newRow]);
  };

  return (
    <div>
      <StepTitle stepNum={1} title="프로젝트 개요" desc="프로젝트 정보를 입력하고, 팀을 구성해보세요."/>
      <div style={{display:'flex', justifyContent:'center'}}>
        <div style={{ display: 'flex', flexDirection: 'column', width: '93%' }}>
          {/*  개요 아래 최상위 div  */}
          <div style={{display: 'flex' }}>

            {/* 사진, 프로젝트 이름, 설명 영역 */}
            <div style={{ display: 'flex', flexDirection: 'column' }}>
              <p className={styles.boldText}>사진</p>
              <img src={sampleSrc} alt="Project" width={355} height={258} style={{ borderRadius: '10px' }} />
            </div>
            <div style={{ display: 'flex', flexDirection: 'column', marginLeft:vars.space.large, width:'50%' }}>
              <p className={styles.boldText}>프로젝트 이름</p>
              <input placeholder="프로젝트 이름" style={{padding:vars.space.small, borderRadius:'5px', border: '1px solid black'}}/>
              <p className={styles.boldText}>프로젝트 설명</p>
              <textarea placeholder="프로젝트 설명" style={{padding:vars.space.small, borderRadius:'5px', height:'30%'}}/>
            </div>
          </div>

          {/* 날짜 설정 영역 */}
          <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'flex-start' }}>
            <p className={styles.boldText}>기간</p>
            <div style={{ display: 'flex', width:'45%', justifyContent:'space-between' }}>
              <StartCalendar />
              <EndCalendar />
            </div>
          </div>

          {/*  협업 툴  */}
          <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'flex-start' }}>
            <div style={{ display: 'flex', alignItems: 'center' }}>
              <p className={styles.boldText}>협업 툴</p>
              <Image src={Add} alt="add" width={32} height={32} onClick={addTool} />
            </div>
            <ToolTable rows={rows} />
          </div>

          {/*  팀원 추가  */}
          <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'flex-start' }}>
            <div style={{ display: 'flex', alignItems: 'center' }}>
              <p className={styles.boldText}>팀원</p>
              <Image src={Add} alt="add" width={32} height={32} onClick={addTeam} />
            </div>
            <TeamTable rows={teamRows} />
          </div>

        </div>
      </div>
    </div>
  )
}

export default Outline