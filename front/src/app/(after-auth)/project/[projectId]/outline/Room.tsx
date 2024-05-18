'use client'

import { ClientSideSuspense } from '@liveblocks/react'
import { usePathname } from 'next/navigation'
import { ReactNode } from 'react'
import { RoomProvider } from '@/../liveblocks.config'
import Loading from '@/app/loading'
import { LiveList, LiveObject } from '@liveblocks/client'
import { ProjectMember, ProjectTool } from '@/containers/outline/types/outlineStorage'

function Room({ children }: { children: ReactNode }) {
  const path = usePathname()

  return (
    <RoomProvider id={path}
                  initialPresence={{}}
                  initialStorage={{
                    outline : new LiveObject({
                      project_name:'Untitled',
                      project_description:'',
                      project_photo:'',
                      project_startDate:'',
                      project_endDate:'',
                      project_tools: new LiveList<LiveObject<ProjectTool>>(),
                      project_team: new LiveList<LiveObject<ProjectMember>>()
                    }),
    }}>
      <ClientSideSuspense fallback={<div><Loading/></div>}>
        {() => children}
      </ClientSideSuspense>
    </RoomProvider>
  )
}

export default Room
