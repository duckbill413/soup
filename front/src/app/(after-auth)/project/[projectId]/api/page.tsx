import { StepTitleWithGuide } from '@/components/project/StepTitle'

export default function ApiSpecification() {
  return (
    <div>
      <StepTitleWithGuide
        stepNum={6}
        title="API 명세서"
        desc="필요한 기능을 정리하고 JIRA와 연동해보세요."
        guideTitle="API 명세서 작성 가이드"
      />
    </div>
  )
}
