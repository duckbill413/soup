'use client'

import { ClientSideSuspense } from '@liveblocks/react'
import { usePathname } from 'next/navigation'
import { ReactNode } from 'react'
import { RoomProvider } from '@/../liveblocks.config'
import Loading from '@/app/loading'
import { LiveList, LiveObject } from '@liveblocks/client'
import { PlanStorage, ProjectTags } from '@/containers/plan/types/planStorage'


function Room({ children }: { children: ReactNode }) {
  const path = usePathname()
  const initialBefore = {
    project_background: new LiveList<LiveObject<ProjectTags>>(),
    project_intro: new LiveList<LiveObject<ProjectTags>>(),
    project_target: new LiveList<LiveObject<ProjectTags>>(),
    project_effect: new LiveList<LiveObject<ProjectTags>>(),
    project_using: false
  };
  const initialAfter = {
    project_background: "",
    project_intro: "",
    project_target: "",
    project_effect: ""
  };
  return (
    <RoomProvider id={path} initialPresence={{}}
                  initialStorage={{
                    plan: new LiveObject<PlanStorage>({
                      before: new LiveObject(initialBefore),
                      after: new LiveObject(initialAfter)
                    })
    }}>
      <ClientSideSuspense fallback={<div><Loading/></div>}>
        {() => children}
      </ClientSideSuspense>
    </RoomProvider>
  )
}

export default Room
