"use client"

import * as styles from '@/containers/plan/styles/plan.css'
import { ChangeEvent, KeyboardEvent, useState } from 'react'
import { LiveObject } from '@liveblocks/client'
import { ProjectTags } from '@/containers/plan/types/planStorage'
import makeAIPlan from '@/apis/plan/planAPI'
import { Toast } from '@/utils/toast'
import { useParams } from 'next/navigation'
import { useMutation, useStorage } from '../../../../../liveblocks.config'

function PlanBeforeAI () {
  const {projectId} = useParams()
  const initialProject = useStorage((root)=>root.plan)
  const [backgroundInput, setBackgroundInput] = useState<string>('');
  const [introInput, setIntroInput] = useState<string>('')
  const [targetInput, setTargetInput] = useState<string>('')
  const [effectInput, setEffectInput] = useState<string>('')

  // 한글키 중복 입력 방지용
  const [isComposingBackground, setIsComposingBackground] = useState(false);
  const [isComposingIntro, setIsComposingIntro] = useState(false);
  const [isComposingTarget, setIsComposingTarget] = useState(false);
  const [isComposingEffect, setIsComposingEffect] = useState(false);

  const updateTags = useMutation(({storage}, action:string, field, tag)=>{
    const tags = storage.get("plan")?.get("before")?.get(field)
    if(action === "add"){
      tags.push(new LiveObject<ProjectTags>(tag))
    } else if (action === "delete"){
      const index = tags.findIndex((t:LiveObject<ProjectTags>)=>t.get("id")===tag);
      if (index !== -1) {
        tags.delete(index);
      }
    } else if (action === "update"){
      storage.get("plan")?.get("before")?.set(field, tag)
    } else {
      storage.get("plan")?.get("after").set(field, tag)
    }
  },[])

  const addHashtag = (inputValue:string, field:string, setter: React.Dispatch<React.SetStateAction<string>>, isComposing: boolean) =>
    (e: KeyboardEvent<HTMLInputElement>) => {
      if (e.key === 'Enter' && !isComposing && inputValue.trim() !== '') {
        updateTags("add",`project_${field}`, {id: crypto.randomUUID(), content: inputValue.trim()});
        setter('');
        e.preventDefault();
      }
  };

  const handleComposition = (setter: React.Dispatch<React.SetStateAction<boolean>>) =>
    (e: React.CompositionEvent) => {
      setter(e.type !=='compositionend')
    }

  const onChangeInput = (setter: React.Dispatch<React.SetStateAction<string>>) =>
    (e: ChangeEvent<HTMLInputElement>) => {
    setter(e.target.value);
  };

  const makePlan = () => {
    if(initialProject?.before.project_using === true){Toast.error("기획서 작성 중입니다"); return;}
    updateTags("update","project_using", true)
    const aiData = {
      background : initialProject?.before?.project_background.map((tag: ProjectTags) => tag.content) || [],
      intro: initialProject?.before?.project_intro.map((tag: ProjectTags) => tag.content) || [],
      target: initialProject?.before?.project_target.map((tag: ProjectTags) => tag.content) || [],
      result: initialProject?.before?.project_effect.map((tag: ProjectTags) => tag.content) || []
    }
    makeAIPlan(`${projectId}`, aiData)
      .then(response => {
        updateTags("fetch","project_background",response.result.background[0])
        updateTags("fetch","project_intro",response.result.intro[0])
        updateTags("fetch","project_target",response.result.target[0])
        updateTags("fetch","project_effect",response.result.result[0])
      }).catch(error=>alert(`기획서 작성 실패 : ${error.message}`))
      .finally(() => updateTags("update","project_using", false))
  }

  return (
    <div className={styles.beforeAIContainer}>
      <p>기획 배경</p>
      <input type="text" placeholder="기획 배경 키워드를 입력해주세요. 엔터키를 눌러 추가하세요."
        value={backgroundInput}
        onChange={onChangeInput(setBackgroundInput)}
        onKeyDown={addHashtag(backgroundInput, "background", setBackgroundInput,isComposingBackground)}
        onCompositionStart={e => handleComposition(setIsComposingBackground)(e)}
        onCompositionEnd={e => handleComposition(setIsComposingBackground)(e)}
      />
      <div className={styles.beforeAIMainDiv}>
        {initialProject?.before?.project_background.map((tag: ProjectTags) => (
          <span key={tag.id} className={styles.tagContainer}>
            <span className={styles.tagDivision}>{tag.content}</span>
            <button type="button" onClick={() => updateTags("delete", "project_background", tag.id)}
                    style={{ color: '#515455' }}>✖</button>
          </span>
        ))}
      </div>

      <p>서비스 소개</p>
      <input type="text" placeholder="서비스 소개 키워드를 입력해주세요. 엔터키를 눌러 추가하세요."
             value={introInput}
             onChange={onChangeInput(setIntroInput)}
             onKeyDown={addHashtag(introInput, 'intro', setIntroInput, isComposingIntro)}
             onCompositionStart={e => handleComposition(setIsComposingIntro)(e)}
             onCompositionEnd={e => handleComposition(setIsComposingIntro)(e)}
      />
      <div className={styles.beforeAIMainDiv}>
        {initialProject?.before?.project_intro.map((tag: ProjectTags) => (
          <span key={tag.id} className={styles.tagContainer}>
            <span className={styles.tagDivision}>{tag.content}</span>
            <button type="button" onClick={() => updateTags("delete", "project_intro", tag.id)}
                    style={{ color: '#515455' }}>✖</button>
          </span>
        ))}
      </div>

      <p>서비스 타겟</p>
      <input type="text" placeholder="서비스 타겟 키워드를 입력해주세요. 엔터키를 눌러 추가하세요."
             value={targetInput}
             onChange={onChangeInput(setTargetInput)}
             onKeyDown={addHashtag(targetInput, 'target', setTargetInput, isComposingTarget)}
             onCompositionStart={e => handleComposition(setIsComposingTarget)(e)}
             onCompositionEnd={e => handleComposition(setIsComposingTarget)(e)}
      />
      <div className={styles.beforeAIMainDiv}>
        {initialProject?.before?.project_target.map((tag: ProjectTags) => (
          <span key={tag.id} className={styles.tagContainer}>
            <span className={styles.tagDivision}>{tag.content}</span>
            <button type="button" style={{ color: '#515455' }}
              onClick={() => updateTags("delete", "project_target", tag.id)}
            >✖</button>
          </span>
        ))}
      </div>

      <p>기대 효과</p>
      <input type="text" placeholder="기대 효과 키워드를 입력해주세요. 엔터키를 눌러 추가하세요."
             value={effectInput}
             onChange={onChangeInput(setEffectInput)}
             onKeyDown={addHashtag(effectInput, 'effect', setEffectInput, isComposingEffect)}
             onCompositionStart={e => handleComposition(setIsComposingEffect)(e)}
             onCompositionEnd={e => handleComposition(setIsComposingEffect)(e)}
      />
      <div className={styles.beforeAIMainDiv}>
        {initialProject?.before?.project_effect.map((tag: ProjectTags) => (
          <span key={tag.id} className={styles.tagContainer}>
            <span className={styles.tagDivision}>{tag.content}</span>
            <button type="button" onClick={() => updateTags("delete", "project_effect", tag.id)}
                    style={{ color: '#515455' }}>✖</button>
          </span>
        ))}
      </div>
      <div className={styles.buttonDiv}>
        <button type="button" className={styles.button} onClick={makePlan}>기획서 생성하기</button>
      </div>
    </div>
  )
}

export default PlanBeforeAI