import { StepTitle } from '@/components/StepTitle/StepTitle'
import ERDDrawing from '@/containers/erd/ERDDrawing'
import * as styles from '@/containers/erd/erd.css'

export default function ERD() {
  return (
    <>
      <div>
        <StepTitle
          stepNum={5}
          title="ERD"
          desc="데이터베이스를 설계하세요"
        />
      </div>
      <div className={styles.container}>
        <ERDDrawing />
      </div>
    </>
  )
}
