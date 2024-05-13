'use client'

import * as styles from '@/containers/build/styles/dependencies.css'
import { Dependency } from '@/types/dependency'
import { DragIndicator, KeyboardArrowRight } from '@mui/icons-material'
import { useRef, useState } from 'react'

// TODO: sample Data 삭제 필
const sampleData: Array<Dependency> = [
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
  {
    id: 7,
    name: 'Rest Repositories',
  },
  {
    id: 8,
    name: 'Rest Repositories',
  },
  {
    id: 9,
    name: 'Rest Repositories',
  },
  {
    id: 10,
    name: 'Rest RepositoriesRest RepositoriesRest Repositories',
  },
  {
    id: 11,
    name: 'Rest Repositories',
  },
  {
    id: 12,
    name: 'Rest Repositories',
  },
]

const sampleData2 = [
  {
    id: 13,
    name: 'Swagger',
  },
  {
    id: 14,
    name: 'JPA',
  },
  {
    id: 15,
    name: 'dev-tools',
  },
  {
    id: 16,
    name: 'Spring Security',
  },
  {
    id: 17,
    name: 'Lombok',
  },
  {
    id: 18,
    name: 'Rest Repositories',
  },
]

export default function Dependencies() {
  const dragItem = useRef<{ type: string; idx: number } | null>(null)
  const dragOverItem = useRef<{ type: string; idx: number } | null>(null)

  const [list1, setList1] = useState<Array<Dependency>>(sampleData)
  const [list2, setList2] = useState<Array<Dependency>>(sampleData2)

  const dragStart = (t: string, i: number) => {
    dragItem.current = { type: t, idx: i }
  }

  const dragEnter = (t: string, i: number) => {
    dragOverItem.current = { type: t, idx: i }
  }

  const drop = () => {
    if (
      dragItem.current &&
      dragOverItem.current &&
      dragItem.current.type !== dragOverItem.current.type
    ) {
      const deletedList =
        dragOverItem.current.type === 'left' ? [...list2] : [...list1]
      const dragItemContent = deletedList[dragItem.current.idx]

      // 선택했던 목록에서 해당 항목 삭제
      deletedList.splice(dragItem.current.idx, 1)

      // 드롭한 목록에 해당 항목 추가
      const addedList =
        dragOverItem.current.type === 'left' ? [...list1] : [...list2]
      addedList.splice(dragOverItem.current.idx, 0, dragItemContent)

      if (dragOverItem.current && dragOverItem.current.type === 'left') {
        setList1(addedList)
        setList2(deletedList)
      } else {
        setList1(deletedList)
        setList2(addedList)
      }
    }
    // ref 초기화
    dragItem.current = null
    dragOverItem.current = null
  }

  const listItem = (
    item: Dependency,
    type: string,
    idx: number,
    style: string,
  ) => (
    <div
      key={item.id}
      className={`${styles.list} ${style}`}
      onDragStart={() => dragStart(type, idx)}
      onDragEnter={() => dragEnter(type, idx)}
      onDragOver={(e) => e.preventDefault()}
      onDragEnd={() => drop()}
      draggable
    >
      <span className={styles.name}>{item.name}</span>
      <DragIndicator sx={{ cursor: 'pointer' }} />
    </div>
  )

  return (
    <div className={styles.dependencies}>
      <h4>Dependencies</h4>
      <section className={styles.section}>
        <div className={`${styles.box} ${styles.whiteBox}`}>
          {list1.map((item, idx) => listItem(item, 'left', idx, styles.white))}
        </div>
        <KeyboardArrowRight />
        <div className={`${styles.box} ${styles.greenBox}`}>
          {list2.map((item, idx) => listItem(item, 'right', idx, styles.green))}
        </div>
      </section>
    </div>
  )
}
