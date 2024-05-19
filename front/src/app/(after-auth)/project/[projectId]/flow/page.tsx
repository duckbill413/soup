import Room from '@/app/(after-auth)/project/[projectId]/flow/Room'
import { StepTitleWithGuide } from '@/components/StepTitle/StepTitle'
import Live from '@/components/cursor/Live'
import { Metadata } from 'next'
import dynamic from 'next/dynamic'

export const metadata: Metadata = {
  title: '플로우 차트',
}

const Mermaid = dynamic(() => import('@/containers/flow/Mermaid'), {
  ssr: false,
})

export default function FlowChart() {

    return (
        <Room>
            <Live>
                <StepTitleWithGuide
                    stepNum={4}
                    title="Flow Chart"
                    desc="서비스 주요 로직을 도식화하세요."
                    guideTitle="플로우 차트 작성 가이드"
                />
                <Mermaid/>
            </Live>
        </Room>
    );
}