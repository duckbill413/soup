import Menu from '@mui/material/Menu'
import MenuItem from '@mui/material/MenuItem'
import { Dispatch, SetStateAction } from 'react'

interface Props {
  handleDelete: Function
  index: number
  anchorEl: HTMLElement | null
  setAnchorEl: Dispatch<SetStateAction<HTMLElement | null>>
}

export default function ContextMenu({
  handleDelete,
  index,
  anchorEl,
  setAnchorEl,
}: Props) {
  const open = Boolean(anchorEl)

  const handleClose = () => {
    setAnchorEl(null)
  }
  return (
    <Menu
      id="context-menu"
      anchorEl={anchorEl}
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
      <MenuItem onClick={() => handleDelete(index)}>삭제</MenuItem>
    </Menu>
  )
}
