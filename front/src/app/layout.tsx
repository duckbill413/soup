import * as styles from '@/styles/global.css'


export default function RootLayout({ children}: Readonly<{ children: React.ReactNode;}>) {
  return (
    <html lang="en">
      <body className={styles.container}>{children}</body>
    </html>)
}
