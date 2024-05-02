import * as styles from "@/containers/outline/styles/table/outlineTable.css";
import React, { useState } from 'react';
import Image from 'next/image'
import deleteIcon from "#/assets/icons/outline/delete.svg"
import editIcon from "#/assets/icons/outline/edit.svg"

interface TableRow {
  name: string;
  description: string;
}

interface ToolTableProps {
  rows: TableRow[];
  deleteTool: (index: number) => void;
  updateTool: (index: number, newName: string, newDescription: string) => void;
}

function ToolTable({ rows, deleteTool, updateTool }: ToolTableProps) {
  const [editingIndex, setEditingIndex] = useState<number | null>(null);
  const [newName, setNewName] = useState<string>('');
  const [newDescription, setNewDescription] = useState<string>('');

  const handleEdit = (index: number) => {
    setEditingIndex(index);
    setNewName(rows[index].name);
    setNewDescription(rows[index].description);
  };

  const handleSave = (index: number) => {
    updateTool(index, newName, newDescription);
    setEditingIndex(null);
  };

  return (
    <table>
      <thead>
      <tr>
        <th className={styles.tableToolTitle}>툴 이름</th>
        <th className={styles.tableURLTitle}>URL 주소</th>
      </tr>
      </thead>
      <tbody>
      {rows.map((row, index) => (
        <tr key={index}>
          <td>
            {editingIndex === index ? (
              <input type="text" value={newName} onChange={(e) => setNewName(e.target.value)} />
            ) : (
              row.name
            )}
          </td>
          <td>
            {editingIndex === index ? (
              <>
              <input type="text" value={newDescription} onChange={(e) => setNewDescription(e.target.value)} />
              <button type="button" onClick={() => handleSave(index)}>저장</button>
              </>
            ) : (
              <>
              <a href={row.description}>{row.description}</a>
              <button type="button" onClick={() => handleEdit(index)}>
                <Image src={editIcon} alt="delete" width={30} height={30}/>
              </button>
              <button type="button" onClick={() => deleteTool(index)}>
                <Image src={deleteIcon} alt="delete" width={30} height={30}/>
              </button>
              </>
            )}
          </td>
        </tr>
      ))}
      </tbody>
    </table>
  );
}

export default ToolTable;
