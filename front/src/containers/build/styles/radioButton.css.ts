import vars from '@/styles/variables.css'
import { style } from '@vanilla-extract/css'

const label = style({
  cursor: 'pointer',

  fontSize: vars.fontSize.caption,
  fontWeight: 500,
  color: vars.color.black,

  display: 'flex',
  alignItems: 'center',
})

export default label
