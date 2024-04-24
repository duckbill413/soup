'use client'

import React, { useEffect, useRef, useState } from 'react'
import * as styles from '@/containers/main/noauth/mainPage.css'
import MainPlan from '@/containers/main/noauth/mainDetails/mainPlan'
import MainIntro from '@/containers/main/noauth/mainDetails/mainIntro'
import MainFunc from '@/containers/main/noauth/mainDetails/mainFunc'
import MainSpec from '@/containers/main/noauth/mainDetails/mainSpec'
import './noScrollBar.css'

export default function MainContent() {
  const outerDivRef = useRef<HTMLDivElement>(null)
  const [currentPage, setCurrentPage] = useState<number>(1)

  const scrollToPage = (page: number) => {
    const pageHeight = window.innerHeight * 0.9
    outerDivRef.current?.scrollTo({
      top: pageHeight * (page - 1),
      left: 0,
      behavior: 'smooth',
    })
    setCurrentPage(page)
  }

  const handleClick = () => {
    if (currentPage < 4) {
      scrollToPage(currentPage + 1)
    }
  }

  useEffect(() => {
    const wheelHandler = (e: WheelEvent) => {
      e.preventDefault()
      if (!outerDivRef.current) return

      const { deltaY } = e
      // const { scrollTop } = outerDivRef.current;

      if (deltaY > 0 && currentPage < 4) {
        scrollToPage(currentPage + 1)
      } else if (deltaY < 0 && currentPage > 1) {
        scrollToPage(currentPage - 1)
      }
    }

    const outerDivRefCurrent = outerDivRef.current
    if (outerDivRefCurrent) {
      outerDivRefCurrent.addEventListener('wheel', wheelHandler)
    }
    return () => {
      if (outerDivRefCurrent) {
        outerDivRefCurrent.removeEventListener('wheel', wheelHandler)
      }
    }
  }, [currentPage])

  return (
    <div ref={outerDivRef} className={styles.pageContainer}>
      <MainIntro onButtonClick={handleClick} />
      <MainPlan />
      <MainFunc />
      <MainSpec />
    </div>
  )
}
