import vars from '@/styles/variables.css'
import { style } from '@vanilla-extract/css'

export const domain = style({
  width: 'fit-content',
  borderRadius: '30px',
  background: vars.color.orange,
  padding: '4px 8px',
  color: 'white',
})

export const link = style({
  color: vars.color.black,
})
