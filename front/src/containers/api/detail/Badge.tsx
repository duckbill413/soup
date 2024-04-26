import { BadgeProps } from '@/types/badge'
import * as styles from '@/containers/api/detail/badge.css'

export default function Badge({ name }: BadgeProps) {
  return <div className={styles.default}>{name}</div>
}
