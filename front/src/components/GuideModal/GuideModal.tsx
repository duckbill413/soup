import * as styles from '@/components/GuideModal/guideModal.css'
import { Close } from '@mui/icons-material'
import { ReactNode } from 'react'
import Draggable from 'react-draggable'

interface GuideProps {
  children: ReactNode
  onClose: () => void
}

function GuideModal({ children, onClose }: GuideProps) {
  return (
    <Draggable>
      <div className={styles.draggable}>
        <div className={styles.modal}>
          <div className={styles.header}>
            <span className={styles.title}>작성 가이드</span>
            <button type="button" onClick={onClose}>
              <Close />
            </button>
          </div>
          <div className={styles.body}>{children}</div>
        </div>
      </div>
    </Draggable>
  )
}

export default GuideModal
