import { makeStyles } from '@mui/styles'

export const useStyles = makeStyles({
  input: {
    '& .MuiOutlinedInput-root': {
      '& fieldset': {
        border: 'none',
        borderRadius: '10px',
      },
    },
  },
  toolInput :{
    '& .MuiOutlinedInput-root': {
      '& fieldset': {
        height:'90%',
        width:'50%',
        borderRadius: '10px',
      },
    },
    '& .MuiInputLabel-root': {
      top: '-9%',
    },
  },
  urlInput: {
    '& .MuiOutlinedInput-root': {
      '& fieldset': {
        height:'90%',
        width:'90%',
        borderRadius: '10px',
      },
    },
    '& .MuiInputLabel-root': {
      top: '-9%',
    },
  },
  teamModalRole : {
    '& .MuiOutlinedInput-root': {
      '& input':{
        height:'10px',
      },
      '& fieldset': {
        borderRadius: '15px',
      },
    },
    '& .MuiInputLabel-root': {
      fontSize: '0.9rem',
      top: '-11%',
    },
  },
});