"use client"

import * as styles from '@/containers/plan/styles/plan.css'
import { ChangeEvent, KeyboardEvent, useState } from 'react'
import { LiveObject } from '@liveblocks/client'
import { ProjectTags } from '@/containers/plan/types/planStorage'
import { useMutation, useStorage } from '../../../../../liveblocks.config'

function PlanBeforeAI () {
  const initialProject = useStorage((root)=>root.plan)
  const [plantagInput, setPlantagInput] = useState<string>('');

  const updateTags = useMutation(({storage}, action:string, field, tag)=>{
    const tags = storage.get("plan")?.get("before")?.get(field)
    if(action === "add"){
      tags.push(new LiveObject<ProjectTags>(tag))
    } else {
      const index = tags.findIndex((t:LiveObject<ProjectTags>)=>t.get("id")===tag);
      if (index !== -1) {
        tags.delete(index);
      }
    }
  },[])

  const addHashtag = (e: KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter' && plantagInput.trim() !== '') {
      updateTags("add","project_background", {id: crypto.randomUUID(), content: plantagInput.trim()});
      setPlantagInput('');
      e.preventDefault();
    }
  };

  const onChangeInput = (e: ChangeEvent<HTMLInputElement>) => {
    setPlantagInput(e.target.value);
  };

  return (
    <div className={styles.beforeAIContainer}>
      <p>기획 배경</p>
      <input
        placeholder="기획 배경 키워드를 입력해주세요. 엔터키를 눌러 추가하세요."
        value={plantagInput}
        onChange={onChangeInput}
        onKeyDown={addHashtag}
      />
      <div className={styles.beforeAIMainDiv}>
        {initialProject?.before?.project_background.map((tag:ProjectTags) => (
          <span key={tag.id} className={styles.tagContainer}>
            <span className={styles.tagDivision}>{tag.content}</span>
            <button type="button" onClick={() => updateTags("delete","project_background",tag.id)} style={{ color: '#515455' }}>✖</button>
          </span>
        ))}
      </div>

      <p>서비스 소개</p>
      <input type="text" placeholder="서비스 소개 키워드를 입력해주세요. 엔터키를 눌러 추가하세요." />
      <p>서비스 타겟</p>
      <input type="text" placeholder="서비스 타겟 키워드를 입력해주세요. 엔터키를 눌러 추가하세요." />
      <p>기대 효과</p>
      <input type="text" placeholder="기대 효과 키워드를 입력해주세요. 엔터키를 눌러 추가하세요." />
      <div className={styles.buttonDiv}>
        <button type="button" className={styles.button}>기획서 생성하기</button>
      </div>
    </div>
  )
}

export default PlanBeforeAI