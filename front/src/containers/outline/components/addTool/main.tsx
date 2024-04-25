"use client"

import Image from 'next/image'
import { useState } from 'react'
import Add from '#/assets/icons/outline/addButton.svg'
import ToolTable from '@/containers/outline/components/addTool/toolTable'
import * as styles from "@/containers/outline/styles/addTool/outlineAddTool.css"

interface RoleTableType {
  name: string;
  description: string;
}

function OutlineAddTool () {
  const [rows, setRows] = useState<RoleTableType[]>([]);
  const addTool = () => {
    const newRow = {
      name: `Tool ${rows.length + 1}`,
      description: 'https://naver.com',
    };
    setRows([...rows, newRow]);
  };

  return (
    <div className={styles.container}>
      <div className={styles.mainDivision}>
        <p>협업 툴</p>
        <Image src={Add} alt="add" width={32} height={32} onClick={addTool} />
      </div>
      <ToolTable rows={rows} />
    </div>
  )
}

export default OutlineAddTool