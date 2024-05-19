'use client'

import { RoomProvider } from '@/../liveblocks.config'
import Loading from '@/app/loading'
import { LiveList } from '@liveblocks/client'
import { ClientSideSuspense } from '@liveblocks/react'
import { usePathname } from 'next/navigation'
import { ReactNode } from 'react'

export default function Room({ children }: { children: ReactNode }) {
  const path = usePathname()

  return (
    <RoomProvider
      id={path}
      initialPresence={{}}
      initialStorage={{ apiList: new LiveList() }}
    >
      <ClientSideSuspense
        fallback={
          <div>
            <Loading />
          </div>
        }
      >
        {() => children}
      </ClientSideSuspense>
    </RoomProvider>
  )
}
