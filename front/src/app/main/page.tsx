import MainPageHeader from '@/containers/main/components/MainPageHeader'
import MainContent from '@/containers/main/components/MainContent'
import * as styles from '@/containers/main/styles/index.css'

export default function MainPage() {
  return (
    <div className={styles.background}>
      <MainPageHeader />
      <MainContent />
    </div>
  )
}
