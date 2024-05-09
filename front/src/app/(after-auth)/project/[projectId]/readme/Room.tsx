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
          readme: new LiveObject({json:""})}}
      >
      {/* Loading 페이지 추가해야 한다. */}
      <ClientSideSuspense fallback={<div><Loading/></div>}>
        {() => children}
      </ClientSideSuspense>
    </RoomProvider>
  )
}

export default Room
