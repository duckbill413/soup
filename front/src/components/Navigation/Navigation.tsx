'use client'

import React, { useState } from 'react'
import * as styles from '@/components/Navigation/navigation.css'
import vars from '@/styles/variables.css'
import { API, build, ERD, flow, func, outline, plan, readme } from '@/../public/assets/icons/navigation'
import { usePathname } from 'next/navigation'
import Link from 'next/link'

type ColorPath ={
  index: number;
  iconPath: string;
}
export default function Navigation() {
  const path = usePathname()
  const slicePath = path.split('/');
  const parentPath = `/${slicePath[1]}/${slicePath[2]}`;
  const [hoverIndex, setHoverIndex] = useState<number>(-1);

  const iconPathMapping = [
    { icon: outline, path: '/outline', name: '개요' },
    { icon: plan, path: '/plan', name: '기획서' },
    { icon: func, path: '/func', name: '기능' },
    { icon: flow, path: '/flow', name: '차트' },
    { icon: ERD, path: '/erd', name: 'ERD' },
    { icon: API, path: '/api', name: 'API' },
    { icon: readme, path: '/readme', name: 'README' },
    { icon: build, path: '/build', name: '빌드' },
  ]

  const determineColor = ({ index, iconPath }:ColorPath) => {
    if (hoverIndex === index) {
      return vars.color.black
    }
    if (path.includes(iconPath)) {
      return vars.color.lightGreen
    }
    return vars.color.white
  }


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
                color={determineColor({ index, iconPath })}
              />
              <p className={`${styles.navName}`}>{name}</p>
            </div>
            <div className={styles.bar} />
          </Link>
        </React.Fragment>
      ))}
    </div>
  )
}
