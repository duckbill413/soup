'use client'

import { useRouter } from 'next/navigation'

export default function AfterAuth() {
const router = useRouter()
  return (
    <div>
      <button  onClick={()=>router.push("/project/1/outline")}>프로젝트로 이동</button>
    </div>
  );
}
