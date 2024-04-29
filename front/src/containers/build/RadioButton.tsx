import { RadioProps } from '@/types/radio'
import { Radio, RadioGroup, FormControlLabel } from '@mui/material'
import * as styles from '@/containers/build/radioButton.css'

export function RadioButton({ checked, label }: RadioProps) {
  return (
    <label className={styles.default}>
      <Radio
        checked={checked}
        value={label}
        name="radio-buttons"
        inputProps={{ 'aria-label': `${label}` }}
      />
      {label}
    </label>
  )
}

export function RadioButtonGroup() {
  return (
    <RadioGroup row defaultValue="3.3.0 (SNAPSHOT)" name="radio-buttons-group">
      <FormControlLabel
        value="3.3.0 (SNAPSHOT)"
        control={<Radio />}
        label="3.3.0 (SNAPSHOT)"
      />
      <FormControlLabel
        value="3.3.0 (M3)"
        control={<Radio />}
        label="3.3.0 (M3)"
      />{' '}
      <FormControlLabel
        value="3.2.5 (SNAPSHOT)"
        control={<Radio />}
        label="3.2.5 (SNAPSHOT)"
      />
      <FormControlLabel value="3.2.4" control={<Radio />} label="3.2.4" />
      <FormControlLabel
        value="3.1.11 (SNAPSHOT)"
        control={<Radio />}
        label="3.1.11 (SNAPSHOT)"
      />
      <FormControlLabel value="3.1.10" control={<Radio />} label="3.1.10" />
    </RadioGroup>
  )
}
