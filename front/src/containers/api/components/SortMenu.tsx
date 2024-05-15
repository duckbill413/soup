'use client'

import { FilterIcon } from '@/../public/assets/icons'
import IconButton from '@/components/IconButton'
import Menu from '@mui/material/Menu'
import MenuItem from '@mui/material/MenuItem'
import * as style from '@/containers/api/styles/index.css'
import { useState, MouseEvent } from 'react'

export default function SortMenu() {
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null)
  const open = Boolean(anchorEl)

  const handleClick = (e: MouseEvent<HTMLButtonElement>) => {
    setAnchorEl(e.currentTarget)
  }

  const handleClickMenu = () => {
    setAnchorEl(null)
  }

  const handleClose = () => {
    setAnchorEl(null)
  }

  return (
    <div className={style.buttonGroup}>
      <IconButton name="정렬 기준" eventHandler={handleClick}>
        <FilterIcon color="currentColor" />
      </IconButton>
      <Menu anchorEl={anchorEl} open={open} onClose={handleClose}>
        <MenuItem onClick={handleClickMenu}>도메인 순</MenuItem>
        <MenuItem onClick={handleClickMenu}>Method 순</MenuItem>
      </Menu>
    </div>
  )
}
