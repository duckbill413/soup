"use client"

import { StepTitleProps } from '@/types/step'
import * as styles from '@/components/StepTitle/stepTitle.css'
import InfoIcon from '@/../public/assets/icons/info'
import { useState } from 'react'
import AiPlanGuide from '@/components/GuideModal/AiPlanGuide'
import FuncGuide from '@/components/GuideModal/FuncGuide'
import FlowChartGuide from '@/components/GuideModal/FlowChartGuide'
import GuideModal from '@/components/GuideModal/GuideModal'
import APIGuide from '@/components/GuideModal/APIGuide'
import BuildGuide from '@/components/GuideModal/BuildGuide'

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
  const [guideContent, setGuideContent] = useState<React.ReactNode>(null)
  const handleModalClose = () => {
    setGuideContent(null);
  };

  const handleModalOpen = () => {
    switch (guideTitle) {
      case '기획서 작성 가이드':
        setGuideContent(<AiPlanGuide/>);
        break;
      case '기능 명세서 작성 가이드':
        setGuideContent(<FuncGuide/>);
        break;
      case '플로우 차트 작성 가이드':
        setGuideContent(<FlowChartGuide/>);
        break;
      case 'API 명세서 작성 가이드':
        setGuideContent(<APIGuide/>);
        break;
      case '빌드 가이드':
        setGuideContent(<BuildGuide/>);
        break;
      default:
        setGuideContent(null);
    }
  };

  return (
    <StepTitle stepNum={stepNum} title={title} desc={desc}>
      <button type="button" className={styles.guideTitle} onClick={handleModalOpen}>
        <InfoIcon color="#535252" />
        {guideTitle}
      </button>
      {(guideContent && (
        <GuideModal onClose={handleModalClose}>
          {guideContent}
        </GuideModal>
      ))}
    </StepTitle>
  )
}
