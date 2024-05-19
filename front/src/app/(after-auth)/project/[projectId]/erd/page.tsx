import Room from '@/app/(after-auth)/project/[projectId]/erd/Room'
import {StepTitleWithGuide} from '@/components/StepTitle/StepTitle'
import Live from '@/components/cursor/Live'
import ERDDrawing from '@/containers/erd/ERDDrawing'
import * as styles from '@/containers/erd/erd.css'
import { Metadata } from 'next'

export const metadata: Metadata = {
  title: 'ERD',
}

type Props = {
  params: { projectId: string }
}
export default function ERD({ params }: Props) {
  const { projectId } = params
  return (
    <Room>
      <Live>
        <StepTitleWithGuide stepNum={5} title="ERD" desc="데이터베이스를 설계하세요"
                            guideTitle="ERD 가이드"/>
        <div className={styles.container}>
          <ERDDrawing projectId={projectId} />
        </div>
      </Live>
    </Room>
  )
}
