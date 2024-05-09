import DetailContents from '@/containers/api/detail/components/DetailContents'
import CloseIcon from '@mui/icons-material/Close'
import { Dialog, Slide } from '@mui/material'
import IconButton from '@mui/material/IconButton'
import Toolbar from '@mui/material/Toolbar'
import Typography from '@mui/material/Typography'
import { TransitionProps } from '@mui/material/transitions'
import { Dispatch, ReactElement, Ref, SetStateAction, forwardRef } from 'react'

const Transition = forwardRef(
  (
    props: TransitionProps & {
      children: ReactElement
    },
    ref: Ref<unknown>,
  ) => <Slide direction="up" ref={ref} {...props} />,
)

interface Props {
  open: boolean
  setOpen: Dispatch<SetStateAction<boolean>>
  idx: number
}

export default function APIDetail({ open, setOpen, idx }: Props) {
  const handleClose = () => {
    setOpen(false)
  }

  return (
    <Dialog
      fullScreen
      open={open}
      onClose={handleClose}
      TransitionComponent={Transition}
    >
      <Toolbar>
        <IconButton
          edge="start"
          color="inherit"
          onClick={handleClose}
          aria-label="close"
        >
          <CloseIcon />
        </IconButton>
        <Typography sx={{ ml: 2, flex: 1 }} variant="h6" component="div">
          API 수정
        </Typography>
      </Toolbar>
      <DetailContents idx={idx} />
    </Dialog>
  )
}
