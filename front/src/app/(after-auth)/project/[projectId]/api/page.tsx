import Room from '@/app/(after-auth)/project/[projectId]/api/Room'
import { StepTitleWithGuide } from '@/components/StepTitle/StepTitle'
import Live from '@/components/cursor/Live'
import APITable from '@/containers/api/components/APITable'

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
          <APITable />
        </div>
      </Live>
    </Room>
  )
}
