import vars from '@/styles/variables.css'
import { style } from '@vanilla-extract/css'

const badge = style({
  width: 'fit-content',
  padding: '8px 16px',

  borderRadius: '30px',
  background: vars.color.black,

  fontSize: vars.fontSize.small,
  color: 'white',
  fontWeight: 500,
})

export default badge
