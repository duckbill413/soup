"use client"

import Image from 'next/image'
import { useState } from 'react'
import Add from '#/assets/icons/outline/addButton.svg'
import TeamTable from '@/containers/outline/components/addTeam/teamTable'
import * as styles from "@/containers/outline/styles/addTeam/outlineAddTeam.css"

interface TeamTableType {
  name: string;
  role: string;
  email: string;
}

function OutlineAddTeam () {
  const [teamRows, setTeamRows] = useState<TeamTableType[]>([]);
  const addTeam = () => {
    const newRow = {
      name: `Tool ${teamRows.length + 1}`,
      role: 'Team',
      email: 'https://naver.com',
    };
    setTeamRows([...teamRows, newRow]);
  };

  return (
    <div className={styles.container}>
      <div className={styles.mainDivision}>
        <p>팀원</p>
        <Image src={Add} alt="add" width={32} height={32} onClick={addTeam} />
      </div>
      <TeamTable rows={teamRows} />
    </div>
  )
}

export default OutlineAddTeam