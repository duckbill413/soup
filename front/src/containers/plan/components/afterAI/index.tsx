'use client'

import Loading from '@/app/loading'
import * as styles from '@/containers/plan/styles/plan.css'
import { useMutation, useStorage } from '../../../../../liveblocks.config'

function PlanAfterAI () {
  const initialProject = useStorage((root)=>root.plan)
  const updateValue = useMutation(({ storage }, field, newValue) => {
    const target = storage.get("plan")?.get("after")
    target?.set(field, newValue)
  }, [])

  if(initialProject?.before.project_using===true) {return (<div className={styles.afterAI}><Loading/></div>)}
  
  return (
    <div className={styles.afterAI}>
      <p>기획 배경</p>
      <textarea value={initialProject?.after.project_background}
                onChange={(e) => updateValue('project_background', e.target.value)}/>
      <p>서비스 소개</p>
      <textarea value={initialProject?.after.project_intro}
                onChange={(e) => updateValue('project_intro', e.target.value)}/>
      <p>서비스 타겟</p>
      <textarea value={initialProject?.after.project_target}
                onChange={(e) => updateValue('project_target', e.target.value)}/>
      <p>기대 효과</p>
      <textarea value={initialProject?.after.project_effect}
                onChange={(e) => updateValue('project_effect', e.target.value)}/>
    </div>
  )
}

export default PlanAfterAI