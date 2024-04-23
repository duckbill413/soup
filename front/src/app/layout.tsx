import type { Metadata } from 'next'
import * as styles from '@/styles/global.css'

export const metadata: Metadata = {
  title: 'SOUP', description: 'Start Organization Upgrade Project',
}

export default function RootLayout({ children}: Readonly<{ children: React.ReactNode;}>) {
  return (
    <html lang="en">
      <body className={styles.container}>
      {children}</body>
    </html>)
}
