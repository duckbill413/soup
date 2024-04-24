'use client'

import * as styles from '@/styles/logo.css'
import { useRouter } from 'next/navigation'
import Logosvg from '@/../public/assets/icons/logo'

type Props={
  logoColor:string;
  leafColor:string;
}
export default function Logo({logoColor,leafColor}:Props){

  const router = useRouter();
  return(
    
    <div onClick={() => router.push("/")} className={styles.logo}>
      <Logosvg logoColor={logoColor} leafColor={leafColor} />
    </div>
  );
}