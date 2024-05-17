"use client"

import Image from 'next/image';
import { useEffect, useState } from 'react'
import Add from '#/assets/icons/outline/addButton.svg';
import ToolTable from '@/containers/outline/components/addTool/ToolTable';
import * as styles from "@/containers/outline/styles/addTool/outlineAddTool.css";
import OutlineToolModal from '@/containers/outline/components/modals/addTool';
import { sendOutlineAPI } from '@/apis/outline/outlineAPI'
import { useParams } from 'next/navigation'

function OutlineAddTool () {
  const {projectId} = useParams()
  const [showModal, setShowModal] = useState(false);
  const clickModal = () => setShowModal(!showModal);

  const addTool = () => {
    setShowModal(!showModal);
  };

  useEffect(() => () => {
    sendOutlineAPI(`${projectId}`).catch(error => alert(error))
  }, [])

  return (
    <div className={styles.container}>
      <div className={styles.mainDivision}>
        <p>협업 툴</p>
        <Image src={Add} alt="add" width={32} height={32} onClick={addTool} />
      </div>
      <ToolTable />
      {showModal && <OutlineToolModal clickModal={clickModal} />}
    </div>
  );
}

export default OutlineAddTool;
