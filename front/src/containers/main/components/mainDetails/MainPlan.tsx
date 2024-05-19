import Image from 'next/image'
import * as styles from '@/containers/main/styles/mainSubPage.css'
import AI from '#/assets/icons/mainpage/plan/illustAI.svg'
import Plan from '#/assets/icons/mainpage/plan/showPlan.svg'

function MainPlan() {
  return (
    <div className={styles.container}>
      <div>
        <div>
          <p className={styles.boldText}>SOUP AI로</p>
          <p className={styles.boldText}>기획서를 간단하게 작성해보세요.</p>
          <p className={styles.text}>기획 배경, 서비스 타겟, 기대 효과 등</p>
          <p className={styles.text}>간단하게 작성하면 기획서가 완성됩니다.</p>
        </div>
        <Image src={AI} alt="Illust" width={364} height={364} />
      </div>
      <div>
        <Image src={Plan} alt="Plan" width={700} height={514} />
      </div>
    </div>
  )
}

export default MainPlan
