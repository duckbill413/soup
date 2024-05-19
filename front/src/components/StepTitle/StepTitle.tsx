'use client'

import InfoIcon from '@/../public/assets/icons/info'
import GuideModal from '@/components/GuideModal/GuideModal'
import { APIText, APITitle } from '@/components/GuideModal/text/APIGuide'
import { BuildText, BuildTitle } from '@/components/GuideModal/text/BuildGuide'
import * as styles from '@/components/StepTitle/stepTitle.css'
import { StepTitleProps } from '@/types/step'
import { useState } from 'react'
import { FlowChartText, FlowChartTitle } from '@/components/GuideModal/text/FlowChartGuide'
import { AiPlanText, AiPlanTitle } from '@/components/GuideModal/text/AiPlanGuide'
import { ERDTitle,ERDText } from '@/components/GuideModal/text/ERDGuide'
import { FuncText, FuncTitle } from '@/components/GuideModal/text/FuncGuide'

export function StepTitle({ stepNum, title, desc, children }: StepTitleProps) {
  return (
    <section className={styles.container}>
      <div>
        <span className={styles.stepNum}>{`Step ${stepNum}.`}</span>
        <span className={styles.title}>{`${title}`}</span>
        <span className={styles.desc}>{` | ${desc}`}</span>
      </div>
      {children}
    </section>
  )
}

export function StepTitleWithGuide({
  stepNum,
  title,
  desc,
  guideTitle,
}: StepTitleProps) {
  const [guideContent, setGuideContent] = useState<{
    titleList: Array<string> | null
    textList: Array<string> | null
  }>({ titleList: null, textList: null })

  const handleModalClose = () => {
    setGuideContent({ titleList: null, textList: null })
  }

  const handleModalOpen = () => {
    if (guideContent.textList && guideContent.titleList) {
      handleModalClose()
      return
    }
    switch (guideTitle) {
      case '기획서 작성 가이드':
        setGuideContent({ titleList: AiPlanTitle, textList: AiPlanText})
        break
      case '기능 명세서 작성 가이드':
         setGuideContent({ titleList: FuncTitle, textList: FuncText })
        break
      case '플로우 차트 작성 가이드':
        setGuideContent({ titleList: FlowChartTitle, textList: FlowChartText })
        break
      case 'ERD 가이드':
        setGuideContent({ titleList: ERDTitle, textList: ERDText  })
        break
      case 'API 명세서 작성 가이드':
        setGuideContent({ titleList: APITitle, textList: APIText })
        break
      case '빌드 가이드':
        setGuideContent({ titleList: BuildTitle, textList: BuildText })
        break
      default:
        setGuideContent({ titleList: null, textList: null })
    }
  }

  return (
    <StepTitle stepNum={stepNum} title={title} desc={desc}>
      <button
        type="button"
        className={styles.guideTitle}
        onClick={handleModalOpen}
      >
        <InfoIcon color="#535252" />
        {guideTitle}
      </button>
      {guideContent.textList && guideContent.titleList ? (
        <GuideModal
          onClose={handleModalClose}
          textList={guideContent.textList}
          titleList={guideContent.titleList}
        />
      ) : null}
    </StepTitle>
  )
}
