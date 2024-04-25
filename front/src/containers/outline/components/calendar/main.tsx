"use client"

import * as styles from '@/containers/outline/styles/calendar/outlineCalendar.css'
import StartCalendar from '@/containers/outline/components/calendar/startCalendar'
import EndCalendar from '@/containers/outline/components/calendar/endCalendar'

function OutlineCalendar () {
  return (
      <div className={styles.container}>
        <p>기간</p>
        <div className={styles.division}>
          <StartCalendar />
          <EndCalendar />
        </div>
    </div>
  )
}

export default OutlineCalendar