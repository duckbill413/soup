import { StepTitleWithGuide } from '@/components/StepTitle/StepTitle'
import Image from 'next/image'
import sortSVG from '@/../public/assets/icons/sort.svg'
import jiraSVG from '@/../public/assets/icons/jira.svg'
import * as styles from '@/containers/func/page.css'
import FuncTable from '@/containers/func/FuncTable'

export default function func() {
  return (<>
    <StepTitleWithGuide stepNum={3} title="기능 명세서" desc="필요한 기능을 정리하고 JIRA와 연동해보세요" guideTitle="기능 명세서 작성 가이드" />
    <div className={styles.tableHeader}>
      <div className={styles.sortHeader}>
        <p>정렬 기준</p>
        <Image src={sortSVG} alt="정렬 아이콘" />
      </div>
      <div>
        <Image src={jiraSVG} alt="지라 아이콘" />
        <p>JIRA 동기화</p>
      </div>
    </div>
  <FuncTable/>

  </>)
}
