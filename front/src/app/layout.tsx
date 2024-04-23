import { Noto_Sans_KR } from 'next/font/google'
import * as styles from '@/styles/global.css'

const notoSansKr = Noto_Sans_KR({ subsets: ['cyrillic'] })

export default function RootLayout({
  children,
}: Readonly<{ children: React.ReactNode }>) {
  return (
    <html lang="en">
      <body className={`${notoSansKr.className} ${styles.container}`}>
        {children}
      </body>
    </html>
  )
}
