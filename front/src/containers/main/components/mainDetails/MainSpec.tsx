import * as styles from '@/containers/main/styles/mainSubPage.css'
import Image from 'next/image'
import illust from '#/assets/icons/mainpage/spec/illustSpec.svg'
import show from '#/assets/icons/mainpage/spec/showSpec.svg'

function MainSpec() {
  return (
    <div className={styles.specContainer}>
      <div>
        <div>
          <p className={styles.boldText}>ERD와 API명세서를 작성하고</p>
          <p className={styles.boldText}>
            Spring 프로젝트를 쉽게 시작해보세요.
          </p>
          <p className={styles.text}>프로젝트 빌드 자동화로</p>
          <p className={styles.text}>
            도메인 기반 프로젝트를 쉽게 시작할 수 있어요.
          </p>
        </div>
        <div>
          <Image src={illust} alt="Illust" width={300} height={300} />
        </div>
      </div>
      <div>
        <Image src={show} alt="show" width={700} height={514} />
      </div>
    </div>
  )
}

export default MainSpec
