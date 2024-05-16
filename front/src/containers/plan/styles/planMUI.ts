import { makeStyles } from '@mui/styles'

export const useStyles = makeStyles({
  input: {
    '& .MuiOutlinedInput-root': {
      '& fieldset': {
        borderRadius: '10px',
      },
    },
  },
});