import Func from '#/assets/icons/mainpage/func/showFunc.svg'
import illust from '#/assets/icons/mainpage/func/illustFunc.svg'
import Image from 'next/image'
import * as styles from '@/containers/main/styles/mainSubPage.css'

function MainFunc() {
  return (
    <div className={styles.funcContainer}>
      <div>
        <Image src={Func} alt="Func" width={700} height={513} />
      </div>
      <div className={styles.funcSubDiv}>
        <p className={styles.boldText}>기능 명세서를 팀원과 동시에 작성하고</p>
        <p className={styles.boldText}>JIRA와 연동해보세요.</p>
        <p className={styles.text}>
          커서로 팀원의 현재 위치를 확인할 수 있어요.
        </p>
        <Image src={illust} alt="illust" width={283} height={283} />
      </div>
    </div>
  )
}

export default MainFunc
