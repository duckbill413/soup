import { StepTitleWithGuide } from '@/components/StepTitle/StepTitle'
import Image from 'next/image'
import jiraSVG from '@/../public/assets/icons/jira.svg'
import * as styles from '@/containers/func/page.css'
import FuncTable from '@/containers/func/FuncTable'
import IconButton from '@/containers/api/IconButton'
import { FilterIcon } from '#/assets/icons'

export default function func() {
  return (<>
    <StepTitleWithGuide stepNum={3} title="기능 명세서" desc="필요한 기능을 정리하고 JIRA와 연동해보세요" guideTitle="기능 명세서 작성 가이드" />
    <div className={styles.tableHeader}>
        <IconButton name="정렬 기준" eventHandler="/sort">
          <FilterIcon color="currentColor" />
        </IconButton>
      <div>
        <Image src={jiraSVG} alt="지라 아이콘" />
        <p>JIRA 동기화</p>
      </div>
    </div>
  <FuncTable/>

  </>)
}
