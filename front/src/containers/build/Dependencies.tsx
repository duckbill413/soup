'use client'

import * as styles from '@/containers/build/dependencies.css'
import { KeyboardArrowRight } from '@mui/icons-material'

// TODO: sample Data 삭제 필
const sampleData = [
  {
    id: 1,
    name: 'Swagger',
  },
  {
    id: 2,
    name: 'JPA',
  },
  {
    id: 3,
    name: 'dev-tools',
  },
  {
    id: 4,
    name: 'Spring Security',
  },
  {
    id: 5,
    name: 'Lombok',
  },
  {
    id: 6,
    name: 'Rest Repositories',
  },
]

const sampleData2 = [
  {
    id: 1,
    name: 'Swagger',
  },
  {
    id: 2,
    name: 'JPA',
  },
  {
    id: 3,
    name: 'dev-tools',
  },
  {
    id: 4,
    name: 'Spring Security',
  },
  {
    id: 5,
    name: 'Lombok',
  },
  {
    id: 6,
    name: 'Rest Repositories',
  },
]

const listItem = (id: number, name: string, style: string) => (
  <div key={id} className={`${styles.list} ${style}`}>
    <span className={styles.name}>{name}</span>
  </div>
)

export default function Dependencies() {
  return (
    <div className={styles.dependencies}>
      <h4>Dependencies</h4>
      <section className={styles.section}>
        <div className={`${styles.box} ${styles.whiteBox}`}>
          {sampleData.map((item: { id: number; name: string }) =>
            listItem(item.id, item.name, styles.white),
          )}
        </div>
        <KeyboardArrowRight />
        <div className={`${styles.box} ${styles.greenBox}`}>
          {sampleData2.map((item: { id: number; name: string }) =>
            listItem(item.id, item.name, styles.green),
          )}
        </div>
      </section>
    </div>
  )
}
