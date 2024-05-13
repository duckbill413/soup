'use client'

import * as styles from '@/containers/build/styles/radioButton.css'
import { RadioProps } from '@/types/radio'
import { FormControlLabel, Radio, RadioGroup } from '@mui/material'
import { useMutation, useStorage } from '../../../../liveblocks.config'

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
  const data = useStorage((root) => root.build)

  const handleChange = useMutation(({ storage }, e) => {
    storage.get('build')?.set('springVersion', e.target.value)
  }, [])

  return (
    <RadioGroup
      row
      value={data?.springVersion}
      name="radio-buttons-group"
      onChange={handleChange}
    >
      <FormControlLabel
        value="3.3.0 (SNAPSHOT)"
        control={<Radio />}
        label="3.3.0 (SNAPSHOT)"
      />
      <FormControlLabel
        value="3.3.0 (M3)"
        control={<Radio />}
        label="3.3.0 (M3)"
      />
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
