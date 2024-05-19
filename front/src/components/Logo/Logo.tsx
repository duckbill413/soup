import Logosvg from '@/../public/assets/icons/logo'
import logo from '@/components/Logo/logo.css'
import Link from 'next/link'

type Props = {
  width: string
  height: string
  logoColor: string
  leafColor: string
}
export default function Logo({ width, height, logoColor, leafColor }: Props) {
  return (
    <Link href="/" className={logo}>
      <Logosvg
        width={width}
        height={height}
        logoColor={logoColor}
        leafColor={leafColor}
      />
    </Link>
  )
}
