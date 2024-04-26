import * as styles from '@/containers/api/input.css'
import { APIInput } from '@/types/apiinput'
import theme from '@/utils/theme'
import {
  MenuItem,
  Select,
  TextField,
  ThemeProvider,
  ButtonGroup,
  Button,
} from '@mui/material'

interface LabelProps {
  title: string
  isEssential: boolean
  children: React.ReactNode
}

function InputLabel({ title, isEssential, children }: LabelProps) {
  return (
    <div className={styles.container}>
      <div className={styles.name}>
        <span className={styles.title}>{title}</span>
        <span
          className={styles.essential}
          style={isEssential ? { display: 'inline' } : { display: 'none' }}
        >
          *
        </span>
      </div>
      {children}
    </div>
  )
}

export function InputText({
  title,
  isEssential,
  placeholder,
  multiline,
}: APIInput) {
  return (
    <InputLabel title={title} isEssential={isEssential}>
      <ThemeProvider theme={theme}>
        <TextField
          variant="outlined"
          placeholder={placeholder}
          sx={{ width: 'fit-content', minWidth: '480px' }}
          multiline={multiline}
        />
      </ThemeProvider>
    </InputLabel>
  )
}

export function Dropbox({ title, isEssential, options }: APIInput) {
  return (
    <InputLabel title={title} isEssential={isEssential}>
      <ThemeProvider theme={theme}>
        <Select
          value={options ? options[0].id : null}
          displayEmpty
          sx={{ width: 'fit-content', minWidth: '200px' }}
        >
          {options?.map((item) => (
            <MenuItem value={item.id} key={item.id}>
              {item.value}
            </MenuItem>
          ))}
        </Select>
      </ThemeProvider>
    </InputLabel>
  )
}

export function MethodButton({ title, isEssential }: APIInput) {
  return (
    <InputLabel title={title} isEssential={isEssential}>
      <ThemeProvider theme={theme}>
        <ButtonGroup variant="outlined" color="secondary" size="large">
          <Button>GET</Button>
          <Button>POST</Button>
          <Button>PUT</Button>
          <Button>DELETE</Button>
          <Button>PATCH</Button>
        </ButtonGroup>
      </ThemeProvider>
    </InputLabel>
  )
}
