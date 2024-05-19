import { StepTitleWithGuide } from '@/components/StepTitle/StepTitle'
import Live from '@/components/cursor/Live'
import { Metadata as BuildMetadata, RadioSection } from '@/containers/build'
import Dependencies from '@/containers/build/components/Dependencies'
import Generate from '@/containers/build/components/Generate'
import * as styles from '@/containers/build/styles/page.css'
import { Metadata } from 'next'
import Room from './Room'

export const metadata: Metadata = {
  title: '프로젝트 빌드',
}

export default function Build() {
  return (
    <Room>
      <Live>
        <div>
          <StepTitleWithGuide
            stepNum={8}
            title="Build"
            desc="ERD와 API 명세서를 기반으로 프로젝트를 빌드합니다."
            guideTitle="빌드 가이드"
          />
          <div className={styles.build}>
            <RadioSection />
            <hr className={styles.line} />
            <section className={styles.row}>
              <BuildMetadata />
              <Dependencies />
            </section>
          </div>
          <Generate />
        </div>
      </Live>
    </Room>
  )
}
