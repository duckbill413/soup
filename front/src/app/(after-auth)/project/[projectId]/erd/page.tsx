import { StepTitleWithGuide } from '@/components/project/StepTitle'
import { ERDDrawing } from '@/components/project/projectDetail/erd/ERDDrawing'
import * as styles from '@/styles/project/projectDetail/erd/erd.css'

export default function ERD() {

  return (<>
    <div>
      <StepTitleWithGuide
        stepNum={5}
        title="ERD"
        desc="데이터베이스를 설계하세요"
      />
    </div>
    <div className={styles.container}>
      <ERDDrawing />
    </div>
  </>)
}