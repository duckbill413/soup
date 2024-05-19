import Markdown from '@/components/GuideModal/Markdown'
import * as styles from '@/components/GuideModal/guideModal.css'
import {
  Close,
  KeyboardArrowLeft,
  KeyboardArrowRight,
} from '@mui/icons-material'
import { useState } from 'react'
import Draggable from 'react-draggable'

interface GuideProps {
  titleList: Array<string>
  textList: Array<string>
  onClose: () => void
}

function GuideModal({ titleList, textList, onClose }: GuideProps) {
  const [step, setStep] = useState(0)

  const handleClick = (num: number) => {
    setStep((prev) => prev + num)
  }
  return (
    <Draggable>
      <div className={styles.draggable}>
        <div className={styles.modal}>
          <div className={styles.header}>
            <div className={styles.step}>
              <h4 style={{ margin: 0 }}>{titleList[step]}</h4>
              <div className={styles.stepButton}>
                <button
                  type="button"
                  onClick={() => handleClick(-1)}
                  disabled={step <= 0}
                  className={styles.button}
                >
                  <KeyboardArrowLeft />
                </button>
                <button
                  type="button"
                  onClick={() => handleClick(1)}
                  disabled={step >= textList.length - 1}
                  className={styles.button}
                >
                  <KeyboardArrowRight />
                </button>
                <span>{`${step + 1} / ${textList.length}`}</span>
              </div>
            </div>
            <button type="button" onClick={onClose} style={{ display: 'flex' }}>
              <Close />
            </button>
          </div>
          <div className={styles.body}>
            <Markdown text={textList[step]} />
          </div>
        </div>
      </div>
    </Draggable>
  )
}

export default GuideModal
