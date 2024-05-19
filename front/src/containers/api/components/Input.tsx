'use client'

import * as styles from '@/containers/api/styles/input.css'
import { APIInput } from '@/containers/api/types/apiinput'
import {
  MenuItem,
  Select,
  TextField,
  ToggleButton,
  ToggleButtonGroup,
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
  value,
  onChange,
}: APIInput) {
  return (
    <InputLabel title={title} isEssential={isEssential}>
      <TextField
        variant="outlined"
        placeholder={placeholder}
        sx={{ width: 'fit-content', minWidth: '480px' }}
        multiline={multiline}
        value={value}
        onChange={onChange}
      />
    </InputLabel>
  )
}

export function Dropbox({
  title,
  isEssential,
  options,
  value,
  onChange,
}: APIInput) {
  return (
    <InputLabel title={title} isEssential={isEssential}>
      <Select
        value={value}
        displayEmpty
        sx={{ width: 'fit-content', minWidth: '200px' }}
        onChange={onChange}
      >
        {options?.map((item) => (
          <MenuItem value={item} key={item}>
            {item}
          </MenuItem>
        ))}
      </Select>
    </InputLabel>
  )
}

export function MethodButton({
  title,
  isEssential,
  value,
  onChange,
}: APIInput) {
  return (
    <InputLabel title={title} isEssential={isEssential}>
      <ToggleButtonGroup
        color="primary"
        value={value}
        exclusive
        onChange={onChange}
        aria-label="Platform"
      >
        <ToggleButton value="GET" sx={{ paddingX: '28px' }}>
          GET
        </ToggleButton>
        <ToggleButton value="POST" sx={{ paddingX: '28px' }}>
          POST
        </ToggleButton>
        <ToggleButton value="PUT" sx={{ paddingX: '28px' }}>
          PUT
        </ToggleButton>
        <ToggleButton value="DELETE" sx={{ paddingX: '28px' }}>
          DELETE
        </ToggleButton>
        <ToggleButton value="PATCH" sx={{ paddingX: '28px' }}>
          PATCH
        </ToggleButton>
      </ToggleButtonGroup>
    </InputLabel>
  )
}
