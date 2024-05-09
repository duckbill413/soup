import * as styles from '@/containers/api/detail/styles/selectType.css'
import Menu from '@mui/material/Menu'
import MenuItem from '@mui/material/MenuItem'
import { MouseEvent, useState } from 'react'

const typeList = [
  {
    name: 'Object',
    color: '#70AE9F',
  },
  {
    name: 'String',
    color: '#8DBC5F',
  },
  {
    name: 'int',
    color: '#DEB06E',
  },
  {
    name: 'Long',
    color: '#DC9C80',
  },
  {
    name: 'yyyyMMDD-hhmmss',
    color: '#93B5E7',
  },
  {
    name: 'yyyyMMDD',
    color: '#D79EBA',
  },
]

const typeChip = (name: string, color: string) => (
  <div style={{ backgroundColor: color }} className={styles.chip}>
    {name}
  </div>
)

interface Props {
  idx: number
  clickMenu: Function
}

export default function SelectType({ idx, clickMenu }: Props) {
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null)
  const open = Boolean(anchorEl)

  const handleClick = (e: MouseEvent<HTMLButtonElement>) => {
    setAnchorEl(e.currentTarget)
  }

  const handleClickMenu = (index: number) => {
    clickMenu(index)
    setAnchorEl(null)
  }

  const handleClose = () => {
    setAnchorEl(null)
  }

  return (
    <div>
      <button
        type="button"
        aria-controls={open ? 'basic-menu' : undefined}
        aria-haspopup="true"
        aria-expanded={open ? 'true' : undefined}
        onClick={handleClick}
      >
        {typeChip(typeList[idx].name, typeList[idx].color)}
      </button>
      <Menu
        id="basic-menu"
        anchorEl={anchorEl}
        open={open}
        onClose={handleClose}
        MenuListProps={{
          'aria-labelledby': 'basic-button',
        }}
      >
        {typeList.map((item, key) => (
          <MenuItem onClick={() => handleClickMenu(key)} key={key}>
            {typeChip(item.name, item.color)}
          </MenuItem>
        ))}
      </Menu>
    </div>
  )
}
