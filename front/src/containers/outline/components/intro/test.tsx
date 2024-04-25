import vars from '@/styles/variables.css'
import sample from '#/assets/icons/mainpage/sample1.jpg'
import { StepTitle } from '@/components/StepTitle/StepTitle'
import Image from 'next/image'
import Add from '#/assets/icons/outline/addButton.svg'
import ToolTable from '@/containers/outline/components/addTool/toolTable'
import { useState } from 'react'
import TeamTable from '@/containers/outline/components/addTeam/teamTable'

interface RoleTableType {
  name: string;
  description: string;
}
interface TeamTableType {
  name: string;
  role: string;
  email: string;
}


function TestPage () {
  const sampleSrc = sample.src
  const [rows, setRows] = useState<RoleTableType[]>([]);
  const [teamRows, setTeamRows] = useState<TeamTableType[]>([]);

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
      <div style={{ display:'flex', justifyContent:'center'}}>
        <div style={{ display: 'flex', flexDirection: 'column', width: '93%' }}>
          <StepTitle stepNum={1} title="프로젝트 개요" desc="프로젝트 정보를 입력하고, 팀을 구성해보세요." />

          {/* 사진 영역 */}
          <div style={{ display: 'flex' }}>
            <div style={{ display: 'flex', flexDirection: 'column' }}>
              <p>사진</p>
              <img src={sampleSrc} alt="Project" width={355} height={258} style={{ borderRadius: '10px' }} />
            </div>
            <div style={{ display: 'flex', flexDirection: 'column', marginLeft: vars.space.large, width: '50%' }}>
              <p>프로젝트 이름</p>
              <input placeholder="프로젝트 이름"
                     style={{ padding: vars.space.small, borderRadius: '5px', border: '1px solid black' }} />
              <p>프로젝트 설명</p>
              <textarea placeholder="프로젝트 설명"
                        style={{ padding: vars.space.small, borderRadius: '5px', height: '30%' }} />
            </div>
          </div>

          {/* 캘린더 영역 */}


          {/* 협업 tool */}
          <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'flex-start' }}>
            <div style={{ display: 'flex', alignItems: 'center' }}>
              <p>협업 툴</p>
              <Image src={Add} alt="add" width={32} height={32} onClick={addTool} />
            </div>
            <ToolTable rows={rows} />
          </div>

          {/*  팀원 추가  */}
          <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'flex-start' }}>
            <div style={{ display: 'flex', alignItems: 'center' }}>
              <p>팀원</p>
              <Image src={Add} alt="add" width={32} height={32} onClick={addTeam} />
            </div>
            <TeamTable rows={teamRows} />
          </div>

        </div>
      </div>
    </div>
  )
}

export default TestPage
