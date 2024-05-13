"use client"

import Image from 'next/image'
import { useState } from 'react'
import Add from '#/assets/icons/outline/addButton.svg'
import TeamTable from '@/containers/outline/components/addTeam/TeamTable'
import * as styles from "@/containers/outline/styles/addTeam/outlineAddTeam.css"
import OutlineTeamModal from '@/containers/outline/components/modals/addTeam'

function OutlineAddTeam () {
  const [showModal, setShowModal] = useState(false)
  const clickModal = () => setShowModal(!showModal)

  const addTeam = () => {
    setShowModal(!showModal)
  };

  return (
    <div className={styles.container}>
      <div className={styles.mainDivision}>
        <p>팀원</p>
        <Image src={Add} alt="add" width={32} height={32} onClick={addTeam} />
      </div>
      <TeamTable showModal={showModal} />
      {showModal && <OutlineTeamModal clickModal={clickModal}/>}
    </div>
  )
}

export default OutlineAddTeam