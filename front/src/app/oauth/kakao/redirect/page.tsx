'use client'

import Loading from '@/app/loading'
import { useRouter } from 'next/navigation'
import { useEffect } from 'react'

export default function KakaoOauth() {
  const router = useRouter()

  useEffect(() => {
    router.replace('/')
  }, [])

  return (
    <div>
      <Loading />
    </div>
  )
}
