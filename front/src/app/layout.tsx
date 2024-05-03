import RecoilRootWrapper from '@/components/RecoilWrapper'
import * as styles from '@/styles/global.css'
import theme from '@/utils/theme'
import { ThemeProvider } from '@mui/material'
import { Noto_Sans_KR } from 'next/font/google'

const notoSansKr = Noto_Sans_KR({ subsets: ['cyrillic'] })

export default function RootLayout({
  children,
}: Readonly<{ children: React.ReactNode }>) {
  console.log('env==', process.env.NEXT_PUBLIC_LIVEBLOCKS_PUBLIC_KEY)
  console.log('host==', process.env.NEXT_PUBLIC_SERVER_HOST)
  console.log('baseURL==', process.env.NEXT_PUBLIC_BACKEND_BASE_URL)

  return (
    <html lang="en">
      <ThemeProvider theme={theme}>
        <body className={`${notoSansKr.className} ${styles.container}`}>
          <RecoilRootWrapper>{children}</RecoilRootWrapper>
        </body>
      </ThemeProvider>
    </html>
  )
}
