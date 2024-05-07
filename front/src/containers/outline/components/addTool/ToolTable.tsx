import * as styles from "@/containers/outline/styles/table/outlineTable.css";
import React, { useState } from 'react';
import Image from 'next/image'
import deleteIcon from "#/assets/icons/outline/delete.svg"
import editIcon from "#/assets/icons/outline/edit.svg"
import { useMutation, useStorage } from '../../../../../liveblocks.config'

function ToolTable() {
  const initialProject = useStorage((root) => root.outline)

  const updateTool = useMutation(({ storage }, action:string, id, updatedName, updatedURL) => {
    const outline = storage.get("outline");
    const tools = outline?.get("project_tools")
    const tool = tools?.find((t)=>t.get("id")===id)
    if (action === "update" && tool) { // 수정하기
      tool.set("name", updatedName)
      tool.set("url", updatedURL);
    } else { // 삭제하기
      const index = tools?.findIndex((t) => t.get("id") === id);
      if (tools && index !== undefined && index !== -1) {
        tools.delete(index);
      }
    }
  }, []);

  const [editingId, setEditingId] = useState<string | null>(null);
  const [newName, setNewName] = useState<string>('');
  const [newURL, setNewURL] = useState<string>('');

  const handleEdit = (id: string, name: string, url?: string) => {
    setEditingId(id);
    setNewName(name);
    setNewURL(url || '');
  };

  const handleSave = (id: string) => {
    updateTool("update", id, newName, newURL);
    setEditingId(null);
  };

  // 따로 함수로 빼둘거
  const normalizeUrl = (url?: string) => {
    const urlString = url ?? "";
    if (!/^https?:\/\//i.test(urlString)) {
      return `https://${urlString}`;
    }
    return urlString;
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
      {initialProject?.project_tools.map((row) => (
        <tr key={row.id}>
          <td>
            {editingId === row.id ? (
              <input type="text" value={newName} onChange={(e) => setNewName(e.target.value)} style={{textAlign:'center'}}/>
            ) : (
              row.name
            )}
          </td>
          <td>
            {editingId === row.id ? (
              <div style={{ display: 'flex', justifyContent: 'flex-start', alignItems: 'center', width: '100%' }}>
                <input type="text" value={newURL} onChange={(e) => setNewURL(e.target.value)} style={{ textAlign:'center', flexGrow: 1, marginRight: '3%' }}/>
                <button type="button" onClick={() => handleSave(row.id)} style={{ marginLeft: 'auto' }}>저장</button>
              </div>
            ) : (
              <div style={{display:'flex', justifyContent:'space-between', alignItems: 'center'}}>
                <div style={{display:'flex', width:'100%',justifyContent:'center'}}>
                <a href={normalizeUrl(row.url)} target="_blank" rel="noopener noreferrer"style={{ textAlign: 'center', marginRight: '10px' }}>{row.url}</a>
                </div>
                <div style={{display:'flex', justifyContent:'flex-end'}}>
                  <button type="button" onClick={() => handleEdit(row.id, row.name, row.url)}style={{ marginLeft: 'auto', marginRight: '3%' }}>
                    <Image src={editIcon} alt="delete" width={30} height={30}/>
                  </button>
                  <button type="button" onClick={() => updateTool("delete", row.id, row.name, row.url)}>
                    <Image src={deleteIcon} alt="delete" width={30} height={30}/>
                  </button>
                </div>
              </div>
            )}
          </td>
        </tr>
      ))}
      </tbody>
    </table>
  );
}

export default ToolTable;
