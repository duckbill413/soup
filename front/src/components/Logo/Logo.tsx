import logo from '@/components/Logo/logo.css'
import Link from 'next/link'
import Logosvg from '@/../public/assets/icons/logo'

type Props = {
  logoColor: string
  leafColor: string
}
export default function Logo({ logoColor, leafColor }: Props) {
  return (
    <Link href="/" className={logo}>
      <Logosvg logoColor={logoColor} leafColor={leafColor} />
    </Link>
  )
}
