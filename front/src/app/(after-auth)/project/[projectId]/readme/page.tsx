import Room from '@/app/(after-auth)/project/[projectId]/readme/Room'
import { StepTitle } from '@/components/StepTitle/StepTitle'
import Live from '@/components/cursor/Live'
import { Metadata } from 'next'
import dynamic from 'next/dynamic'

export const metadata: Metadata = {
  title: 'README',
}

type Props = {
  params: { projectId: string }
}

export default function ReadMe({ params }: Props) {
  const { projectId } = params
  const ToastEditor = dynamic(() => import('@/containers/readme/ToastEditor'), {
    ssr: false,
  })
  return (
    <Room>
      <Live>
        <StepTitle
          stepNum={8}
          title="README"
          desc="프로젝트 기획, 설계 부분의 REDAME.md를 작성해보세요."
        />
        <ToastEditor projectId={projectId} />
      </Live>
    </Room>
  )
}
