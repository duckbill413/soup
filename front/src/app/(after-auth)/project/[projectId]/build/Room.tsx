'use client'

import { RoomProvider } from '@/../liveblocks.config'
import Loading from '@/app/loading'
import { Dependency } from '@/types/dependency'
import { LiveList, LiveObject } from '@liveblocks/client'
import { ClientSideSuspense } from '@liveblocks/react'
import { usePathname } from 'next/navigation'
import { ReactNode } from 'react'

export default function Room({ children }: { children: ReactNode }) {
  const path = usePathname()

  return (
    <RoomProvider
      id={path}
      initialPresence={{}}
      initialStorage={{
        build: new LiveObject({
          project: 'Gradle - Groovy',
          language: 'Java',
          springVersion: '3.3.0 (SNAPSHOT)',
          metadata: new LiveObject({
            group: 'com.example',
            artifact: 'demo',
            name: 'demo',
            description: 'Demo project for Spring Boot',
            packageName: 'com.example.demo',
          }),
          packaging: 'Jar',
          javaVersion: '17',
          dependencies: new LiveList<Dependency>(),
        }),
      }}
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
