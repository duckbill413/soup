'use client'

import { getDependenciesAPI } from '@/apis/build'
import * as styles from '@/containers/build/styles/dependencies.css'
import { Dependency } from '@/types/dependency'
import { DragIndicator, KeyboardArrowRight } from '@mui/icons-material'
import Tooltip from '@mui/material/Tooltip'
import { useEffect, useRef, useState } from 'react'
import { useMutation, useStorage } from '../../../../liveblocks.config'

export default function Dependencies() {
  // drag에 필요한 ref들 목록
  const dragItem = useRef<{ type: string; idx: number } | null>(null)
  const dragOverItem = useRef<{ type: string; idx: number } | null>(null)

  let initialInnerHeight: any
  let el: any

  if (typeof window !== 'undefined') {
    initialInnerHeight = window.innerHeight
  }
  if (typeof document !== 'undefined') {
    el = document.getElementById('rightBox')
  }

  // 전체 dependency 목록
  const [all, setAll] = useState<Array<Dependency>>([])

  // liveblocks storage
  const selectedIdList = useStorage((root) => root.build?.dependencies)

  const handleChange = useMutation(({ storage }, id) => {
    if (
      !storage
        .get('build')
        ?.get('dependencies')
        ?.find((num) => num === id)
    )
      storage.get('build')?.get('dependencies')?.push(id)
  }, [])

  const handleDelete = useMutation(({ storage }, id) => {
    const valueIndex = storage
      .get('build')
      ?.get('dependencies')
      ?.findIndex((num) => num === id)
    if (valueIndex) storage.get('build')?.get('dependencies').delete(valueIndex)
    else console.log('없는 id')
  }, [])

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
      if (dragOverItem.current.type === 'left') {
        handleDelete(dragItem.current.idx)
      } else {
        handleChange(dragItem.current.idx)
      }
    }
    // ref 초기화
    dragItem.current = null
    dragOverItem.current = null
  }

  const listItem = (item: Dependency, type: string, style: string) => (
    <div
      key={item.id}
      className={`${styles.list} ${style}`}
      onDragStart={() => dragStart(type, item.id)}
      onDragEnter={() => dragEnter(type, item.id)}
      onDragOver={(e) => e.preventDefault()}
      onDragEnd={() => drop()}
      draggable={!item.basic}
    >
      <Tooltip title={item.description} placement="top-start" arrow>
        <span className={styles.name}>{item.name}</span>
      </Tooltip>
      {item.basic ? null : <DragIndicator sx={{ cursor: 'pointer' }} />}
    </div>
  )

  const fetchData = async () => {
    const res = await getDependenciesAPI()
    const temp: Array<Dependency> = res.result
    setAll(temp)
    temp.forEach((item) => (item.basic ? handleChange(item.id) : null))
  }

  const handleTouch = () => {
    if (initialInnerHeight && el) {
      el.scrollTo({ top: el.scrollHeight, behavior: 'smooth' })
    }
  }

  useEffect(() => {
    fetchData()
  }, [])

  useEffect(() => {
    handleTouch()
  }, [el, selectedIdList])

  return (
    <div className={styles.dependencies}>
      <h4>Dependencies</h4>
      <section className={styles.section}>
        <div className={`${styles.box} ${styles.whiteBox}`}>
          {all.map((item) => {
            if (!selectedIdList?.find((num) => num === item.id)) {
              return listItem(item, 'left', styles.white)
            }
            return null
          })}
        </div>
        <KeyboardArrowRight />
        <div className={`${styles.box} ${styles.greenBox}`} id="rightBox">
          {all.length > 0 &&
            selectedIdList?.map((num) => {
              const findIndex = all.findIndex((item) => num === item.id)
              // const findIndex = 0
              if (findIndex.toString()) {
                return listItem(all[findIndex], 'right', styles.green)
              }
              return null
            })}
        </div>
      </section>
    </div>
  )
}
