import { StepTitleProps } from '@/types/step'
import * as styles from '@/styles/project/stepTitle.css'
import InfoIcon from '@/../public/assets/icons/info'

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
  return (
    <StepTitle stepNum={stepNum} title={title} desc={desc}>
      <button type="button" className={styles.guideTitle}>
        <InfoIcon color="#535252" />
        {guideTitle}
      </button>
    </StepTitle>
  )
}
