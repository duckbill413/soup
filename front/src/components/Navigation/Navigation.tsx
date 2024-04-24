'use client'
/*
 Navigation(Left)
 */
import React, { useState } from 'react'
import * as styles from '@/components/Navigation/navigation.css'
import vars from '@/styles/variables.css'
import {
  API,
  build,
  ERD,
  flow,
  func,
  outline,
  plan,
  readme,
} from '@/../public/assets/icons/navigation'
import { usePathname } from 'next/navigation'
import Link from 'next/link'

export default function Navigation() {
  // CurrentPath
  const path = usePathname()

  // ParentPath
  const parentPath = path.slice(0, path.lastIndexOf('/'))

  // HoverMouse Point
  const [hoverIndex, setHoverIndex] = useState<number>(-1)

  // Navigation entities
  const iconPathMapping = [
    { icon: outline, path: '/outline', name: '개요' },
    { icon: plan, path: '/plan', name: '계획서' },
    { icon: func, path: '/func', name: '기능' },
    { icon: flow, path: '/flow', name: '차트' },
    { icon: ERD, path: '/erd', name: 'ERD' },
    { icon: API, path: '/api', name: 'API' },
    { icon: build, path: '/build', name: '빌드' },
    { icon: readme, path: '/readme', name: 'README' },
  ]

  return (
    <div className={styles.container}>
      {iconPathMapping.map(({ icon: Icon, path: iconPath, name }, index) => (
        <React.Fragment key={iconPath}>
          <Link
            href={`${parentPath}${iconPath}`}
            style={{ textDecoration: 'none', color: 'white' }}
          >
            <div
              className={styles.icon}
              onMouseEnter={() => setHoverIndex(index)}
              onMouseLeave={() => setHoverIndex(-1)}
            >
              <Icon
                color={
                  hoverIndex === index
                    ? vars.color.black
                    : path.includes(iconPath)
                      ? vars.color.lightGreen
                      : vars.color.white
                }
              />
              <p className={`${styles.navName}`}>{name}</p>
            </div>
            {/* 아이콘 아래 있는 회색 바 */}
            <div className={styles.bar} />
          </Link>
        </React.Fragment>
      ))}
    </div>
  )
}
