import { FilterIcon } from '@/../public/assets/icons'
import Room from '@/app/(after-auth)/project/[projectId]/api/Room'
import IconButton from '@/components/IconButton'
import { StepTitleWithGuide } from '@/components/StepTitle/StepTitle'
import Live from '@/components/cursor/Live'
import APITable from '@/containers/api/components/APITable'
import * as style from '@/containers/api/styles/index.css'

export default function ApiSpecification() {
  return (
    <Room>
      <Live>
        <div>
          <StepTitleWithGuide
            stepNum={6}
            title="API 명세서"
            desc="ERD를 기반으로 API 명세서를 작성해보세요."
            guideTitle="API 명세서 작성 가이드"
          />
          <div className={style.buttonGroup}>
            <IconButton name="정렬 기준" eventHandler="/">
              <FilterIcon color="currentColor" />
            </IconButton>
          </div>
          <APITable />
        </div>
      </Live>
    </Room>
  )
}
