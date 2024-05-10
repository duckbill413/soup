import Menu from '@mui/material/Menu'
import MenuItem from '@mui/material/MenuItem'
import { Dispatch, SetStateAction } from 'react'

interface Props {
  handleDelete: Function
  index: number
  anchorEl: {
    el: HTMLElement
    selectedIndex: number
  } | null
  setAnchorEl: Dispatch<
    SetStateAction<{
      el: HTMLElement
      selectedIndex: number
    } | null>
  >
}

export default function ContextMenu({
  handleDelete,
  index,
  anchorEl,
  setAnchorEl,
}: Props) {
  const open = Boolean(anchorEl?.selectedIndex === index)

  const handleClose = () => {
    setAnchorEl(null)
  }
  return (
    <Menu
      id="context-menu"
      anchorEl={anchorEl?.el}
      open={open}
      onClose={handleClose}
      MenuListProps={{
        'aria-labelledby': 'context-button',
      }}
      anchorOrigin={{
        vertical: 'bottom',
        horizontal: 'right',
      }}
      transformOrigin={{
        vertical: 'top',
        horizontal: 'right',
      }}
    >
      <MenuItem
        onClick={(e) => {
          e.stopPropagation()
          handleDelete(index)
        }}
      >
        삭제
      </MenuItem>
    </Menu>
  )
}
