'use client'

import { RoomProvider } from '@/../liveblocks.config'
import Loading from '@/app/loading'
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
          type: 'Gradle-Groovy',
          language: 'Java',
          languageVersion: '17',
          version: '3.3.0-SNAPSHOT',
          packaging: 'Jar',
          group: 'com.example',
          artifact: 'demo',
          name: 'demo',
          description: 'demo project with soup!',
          packageName: 'com.example.demo',
          dependencies: new LiveList<number>(),
          builtAt: '',
          building: false,
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
