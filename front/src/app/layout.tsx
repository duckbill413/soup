import RecoilRootWrapper from '@/components/RecoilWrapper'
import * as styles from '@/styles/global.css'
import theme from '@/utils/theme'
import { ThemeProvider } from '@mui/material'
import { Metadata } from 'next'
import { Noto_Sans_KR } from 'next/font/google'

const notoSansKr = Noto_Sans_KR({ subsets: ['cyrillic'] })

export const metadata: Metadata = {
  title: 'Start With Soup | Soup',
  description: 'Soup로 프로젝트를 쉽게 시작해보세요.',
}

export default function RootLayout({
  children,
}: Readonly<{ children: React.ReactNode }>) {
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
