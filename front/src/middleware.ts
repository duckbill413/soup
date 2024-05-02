// middleware.ts
import { NextResponse, type NextRequest } from 'next/server'

const AUTH_PAGES = ['/main']

export default function middleware(request: NextRequest) {
  const { nextUrl, cookies } = request
  const { origin, pathname, searchParams } = nextUrl
  const accessToken = cookies.get('accessToken')

  // 로그인 페이지
  if (pathname.startsWith('/oauth')) {
    const newAccessToken = searchParams.get('access-token')
    const newRefreshToken = searchParams.get('refresh-token')
    if (newAccessToken && newRefreshToken) {
      // const response = NextResponse.redirect(new URL('/', origin))
      const response = NextResponse.next()
      response.cookies.set('accessToken', newAccessToken)
      response.cookies.set('refreshToken', newRefreshToken)
      return response
    }
  }

  // 로그인이 필요 없는 페이지
  if (AUTH_PAGES.some((page) => pathname.startsWith(page))) {
    // 로그인 되어 있는 경우 메인 페이지로 리다이렉트
    if (accessToken) {
      return NextResponse.redirect(new URL('/', origin))
    }
    // 로그인이 필요 없는 페이지는 그냥 다음 요청으로 진행
    return NextResponse.next()
  }

  // 로그인이 필요한 페이지
  if (!accessToken) {
    // 로그인 페이지로 리다이렉트
    return NextResponse.redirect(new URL('/main', origin))
  }

  // 로그인 되어 있는 경우 요청 페이지로 진행
  return NextResponse.next()
}

export const config = {
  matcher: ['/((?!healthy|api|_next/static|_next/image|favicon.ico).*)'],
}
