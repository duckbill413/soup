import dynamic from 'next/dynamic';
import { StepTitleWithGuide } from '@/components/StepTitle/StepTitle'

const Mermaid = dynamic(() => import('@/containers/flow/Mermaid'), { ssr: false });

export default function FlowChart() {

  return (
    <>
      <StepTitleWithGuide
        stepNum={3}
        title="Flow Chart"
        desc="서비스 주요 로직을 도식화하세요."
        guideTitle="플로우 차트 작성 가이드"
      />
      <Mermaid/>
    </>
  );
}