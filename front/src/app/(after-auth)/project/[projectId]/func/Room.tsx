'use client'

import { ClientSideSuspense } from '@liveblocks/react'
import { usePathname } from 'next/navigation'
import { ReactNode } from 'react'
import { RoomProvider } from '@/../liveblocks.config'
import Loading from '@/app/loading'
import {LiveList, LiveObject} from '@liveblocks/client'
import {FuncDescResWithColor} from "@/containers/func/types/functionDesc";

function Room({ children }: { children: ReactNode }) {
  const path = usePathname()
  return (
      <RoomProvider id={path} initialPresence={{}} initialStorage={{
          func: new LiveList<LiveObject<FuncDescResWithColor>>}}
      >
      {/* Loading 페이지 추가해야 한다. */}
      <ClientSideSuspense fallback={<div><Loading/></div>}>
        {() => children}
      </ClientSideSuspense>
    </RoomProvider>
  )
}

export default Room
