import * as styles from '@/containers/project/page.css'
import Header from '@/components/Header/Header'
import Navigation from '@/components/Navigation/Navigation'
import Chat from '@/containers/project/Chat'
import Room from '@/app/(after-auth)/project/[projectId]/Room'

export default function ProjectDetailLayout({
  children,
}: Readonly<{ children: React.ReactNode }>) {
  return (
      <div className={styles.container}>
    <Room>
        <Navigation />
        <Header theme='black' useVoice/>
        <div className={styles.content}>{children}</div>
        <Chat />
    </Room>
      </div>
  )
}
