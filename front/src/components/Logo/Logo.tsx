'use client'

import logo from '@/components/Logo/logo.css'
import { useRouter } from 'next/navigation'
import Logosvg from '@/../public/assets/icons/logo'

type Props = {
  logoColor: string
  leafColor: string
}
export default function Logo({ logoColor, leafColor }: Props) {
  const router = useRouter()
  return (
    <div onClick={() => router.push('/')} className={logo}>
      <Logosvg logoColor={logoColor} leafColor={leafColor} />
    </div>
  )
}
