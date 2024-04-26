"use client"

import vars from '@/styles/variables.css'
import * as styles from '@/containers/plan/styles/plan.css'
import { ChangeEvent, KeyboardEvent, useState } from 'react'

interface Plantag {
  content: string;
  id: string;
}

function PlanBeforeAI () {
  const [plantags, setPlantags] = useState<Plantag[]>([]);
  const [plantagInput, setPlantagInput] = useState<string>(''); // 입력 필드 상태

  const addHashtag = (e: KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter' && plantagInput.trim() !== '') {
      setPlantags((prev) => [...prev, { content: plantagInput.trim(), id: crypto.randomUUID() }]);
      setPlantagInput(''); // 입력 필드 초기화
      e.preventDefault();
    }
  };

  const removeHashtag = (id: string) => {
    setPlantags((prev) => prev.filter((tag) => tag.id !== id));
  };

  const onChangeInput = (e: ChangeEvent<HTMLInputElement>) => {
    setPlantagInput(e.target.value);
  };

  return (
    <div style={{ display: 'flex', flexDirection: 'column', width: '42%', height: '100%' }}>
      <p>기획 배경</p>
      <input
        placeholder="기획 배경 키워드를 입력해주세요. 엔터키를 눌러 추가하세요."
        value={plantagInput}
        onChange={onChangeInput}
        onKeyDown={addHashtag}
      />
      <div style={{ display: 'flex', flexWrap: 'wrap', margin: '0.3rem' }}>
        {plantags.map((tag) => (
          <span key={tag.id}
                style={{ backgroundColor: '#9FE0FC', borderRadius: '6px', margin: '0.1rem', padding: '0.2rem' }}>
                  <span style={{ paddingRight: '0.3em' }}>{tag.content}</span>
                  <button type="button" onClick={() => removeHashtag(tag.id)} style={{ color: '#515455' }}>X</button>
                </span>
        ))}
      </div>

      <p>서비스 소개</p>
      <input type="text" placeholder="서비스 소개 키워드를 입력해주세요. 엔터키를 눌러 추가하세요." />
      <p>서비스 타겟</p>
      <input type="text" placeholder="서비스 타겟 키워드를 입력해주세요. 엔터키를 눌러 추가하세요." />
      <p>기대 효과</p>
      <input type="text" placeholder="기대 효과 키워드를 입력해주세요. 엔터키를 눌러 추가하세요." />
      <div style={{ height: '6%', display: 'flex', justifyContent: 'center', marginTop: vars.space.large }}>
        <button type="button" className={styles.button}>기획서 생성하기</button>
      </div>
    </div>
  )
}

export default PlanBeforeAI