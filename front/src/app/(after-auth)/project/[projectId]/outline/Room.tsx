'use client'

import { ClientSideSuspense } from '@liveblocks/react'
import { usePathname } from 'next/navigation'
import { ReactNode } from 'react'
import { RoomProvider } from '@/../liveblocks.config'
import Loading from '@/app/loading'
import { LiveList, LiveObject } from '@liveblocks/client'
import { ProjectMember, ProjectTool, Role } from '@/containers/outline/types/outlineStorage'

function Room({ children }: { children: ReactNode }) {
  const path = usePathname()

  const initialRoles = new LiveList<LiveObject<Role>>();  // 역할 리스트 초기화
  const initialMembers = new LiveList<LiveObject<ProjectMember>>([
    new LiveObject<ProjectMember>({
      id: '',
      name: '',
      roles: initialRoles,
      email: '',
      jira: false
    })
  ]);
  return (
    <RoomProvider id={path}
                  initialPresence={{}}
                  initialStorage={{
                    outline : new LiveObject({
                      project_name:'',
                      project_description:'',
                      project_photo:'',
                      project_startDate:'',
                      project_endDate:'',
                      project_tools: new LiveList<LiveObject<ProjectTool>>(),
                      project_team: initialMembers
                    }),
    }}>
      <ClientSideSuspense fallback={<div><Loading/></div>}>
        {() => children}
      </ClientSideSuspense>
    </RoomProvider>
  )
}

export default Room
