import Link from 'next/link'

export default function LoginButton() {
  const isDev = process.env.NODE_ENV === 'development' ?? false

  const loginURL = isDev
    ? `${process.env.NEXT_PUBLIC_CLIENT_HOST}/local-login`
    : `${process.env.NEXT_PUBLIC_SERVER_HOST}/oauth2/authorization/kakao`

  return (
    <Link href={loginURL}>
      <img
        src="//k.kakaocdn.net/14/dn/btroDszwNrM/I6efHub1SN5KCJqLm1Ovx1/o.jpg"
        width="180"
        alt="카카오 로그인 버튼"
      />
    </Link>
  )
}
