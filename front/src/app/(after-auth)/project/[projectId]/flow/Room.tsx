'use client'

import { ClientSideSuspense } from '@liveblocks/react'
import { usePathname } from 'next/navigation'
import { ReactNode } from 'react'
import { RoomProvider } from '@/../liveblocks.config'
import Loading from '@/app/loading'
import {LiveObject} from '@liveblocks/client'

function Room({ children }: { children: ReactNode }) {
  const path = usePathname()
  return (
      <RoomProvider id={path} initialPresence={{}} initialStorage={{
          flow: new LiveObject({json:`\`\`\`mermaid
          flowchart TD
            A[시작] -->|"주요 흐름"| B[과정 1]
            B --> C{결정해야 할 사항}
            C -->|"예"| D[옵션 1]
            C -->|"아니오"| E[옵션 2]
            D -.-> F[최종 단계]
            E ==>|"중요한 결정"| G[중요한 최종 단계]
            F --> H[종료]
            G --> H\n\`\`\`
        `})}}
      >
      {/* Loading 페이지 추가해야 한다. */}
      <ClientSideSuspense fallback={<div><Loading/></div>}>
        {() => children}
      </ClientSideSuspense>
    </RoomProvider>
  )
}

export default Room
