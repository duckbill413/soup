import { Noto_Sans_KR } from 'next/font/google'
import * as styles from '@/styles/global.css'
import theme from '@/utils/theme'
import { ThemeProvider } from '@mui/material'

const notoSansKr = Noto_Sans_KR({ subsets: ['cyrillic'] })

export default function RootLayout({
  children,
}: Readonly<{ children: React.ReactNode }>) {
  return (
    <html lang="en">
      <ThemeProvider theme={theme}>
        <body className={`${notoSansKr.className} ${styles.container}`}>
          {children}
        </body>
      </ThemeProvider>
    </html>
  )
}
