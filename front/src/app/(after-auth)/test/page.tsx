'use client'

import { test1 } from '@/apis/test/testAPI'
import { tokenClear } from '@/utils/token'

export default function Test() {
  const handleClick = async () => {
    await test1()
  }

  const handleClick2 = async () => {
    tokenClear()
    window.location.href = '/main'
  }

  return (
    <div>
      <button type="button" onClick={handleClick}>
        token expire and refresh token test
      </button>

      <button type="button" onClick={handleClick2}>
        logout test
      </button>
    </div>
  )
}
