"use client"

import Image from 'next/image';
import { useEffect, useState } from 'react'
import Add from '#/assets/icons/outline/addButton.svg';
import ToolTable from '@/containers/outline/components/addTool/ToolTable';
import * as styles from "@/containers/outline/styles/addTool/outlineAddTool.css";
import OutlineToolModal from '@/containers/outline/components/modals/addTool';
import { sendOutlineAPI } from '@/apis/outline/outlineAPI'
import { useParams } from 'next/navigation'
import { useStorage } from '../../../../../liveblocks.config'

function OutlineAddTool () {
  const {projectId} = useParams()
  const outlineData = useStorage((root) => root.outline)
  const [showModal, setShowModal] = useState(false);
  const clickModal = () => setShowModal(!showModal);

  const addTool = () => {
    setShowModal(!showModal);
  };

  useEffect(() => () => {
    if(outlineData?.project_name !== undefined && outlineData?.project_photo !== undefined) {
      sendOutlineAPI(`${projectId}`,
        {
          name: outlineData.project_name,
          description: outlineData?.project_description,
          imgUrl: outlineData.project_photo,
          startDate: outlineData?.project_startDate,
          endDate: outlineData?.project_endDate,
          tools : null
        }).catch(error => alert(error))
     }
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
