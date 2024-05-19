import vars from '@/styles/variables.css'
import { style } from '@vanilla-extract/css'

const badge = style({
  width: 'fit-content',
  padding: '8px 16px',
  margin: '8px 0',

  borderRadius: '30px',
  background: vars.color.black,

  fontSize: vars.fontSize.small,
  color: 'white',
  fontWeight: 500,
})

export default badge
