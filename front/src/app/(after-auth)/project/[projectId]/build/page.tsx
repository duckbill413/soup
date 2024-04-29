import { StepTitleWithGuide } from '@/components/StepTitle/StepTitle'
import { Metadata, RadioSection } from '@/containers/build'
import Dependencies from '@/containers/build/Dependencies'
import * as styles from '@/containers/build/page.css'

export default function Build() {
  return (
    <div>
      <StepTitleWithGuide
        stepNum={7}
        title="Build"
        desc="ERD와 API 명세서를 기반으로 프로젝트를 빌드합니다."
        guideTitle="빌드 가이드"
      />
      <div className={styles.build}>
        <RadioSection />
        <hr className={styles.line} />
        <section className={styles.row}>
          <Metadata />
          <Dependencies />
        </section>
      </div>
    </div>
  )
}
