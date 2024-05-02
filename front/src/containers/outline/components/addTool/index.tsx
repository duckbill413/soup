"use client"

import Image from 'next/image';
import { useState } from 'react';
import Add from '#/assets/icons/outline/addButton.svg';
import ToolTable from '@/containers/outline/components/addTool/ToolTable';
import * as styles from "@/containers/outline/styles/addTool/outlineAddTool.css";
import OutlineToolModal from '@/containers/outline/components/modals/addTool';

interface RoleTableType {
  name: string;
  description: string;
}

function OutlineAddTool () {
  const [rows, setRows] = useState<RoleTableType[]>([]);
  const [showModal, setShowModal] = useState(false);
  const clickModal = () => setShowModal(!showModal);

  const addTool = () => {
    const newRow = {
      name: `Tool ${rows.length + 1}`,
      description: `https://naver.com ${rows.length + 1}`,
    };
    setRows([...rows, newRow]);
    setShowModal(!showModal);
  };

  const deleteTool = (index: number) => {
    setRows(rows.filter((_, idx) => idx !== index));
  };

  const updateTool = (index: number, newName: string, newDescription: string) => {
    const updatedRows = rows.map((row, idx) =>
      idx === index ? { ...row, name: newName, description: newDescription } : row
    );
    setRows(updatedRows);
  };

  return (
    <div className={styles.container}>
      <div className={styles.mainDivision}>
        <p>협업 툴</p>
        <Image src={Add} alt="add" width={32} height={32} onClick={addTool} />
      </div>
      <ToolTable rows={rows} deleteTool={deleteTool} updateTool={updateTool} />
      {showModal && <OutlineToolModal clickModal={clickModal} />}
    </div>
  );
}

export default OutlineAddTool;
