import * as styles from '@/styles/project/projectDetail/page.css'
import Header from '@/components/project/Header'
import Navigation from '@/components/project/Navigation'
import Chat from '@/components/project/projectDetail/Chat'

export default function ProjectDetailLayout({ children }: Readonly<{ children: React.ReactNode; }>) {
  return (<div className={styles.container}>
    <Navigation />
    <Header />
    <div className={styles.content}>
        {children}
    </div>
    <Chat />
  </div>)
}
