import * as styles from '@/containers/api/detail/styles/badge.css'
import { BadgeProps } from '@/containers/api/detail/types/badge'

export default function Badge({ name }: BadgeProps) {
  return <div className={styles.default}>{name}</div>
}
