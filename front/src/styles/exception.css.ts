import { style } from '@vanilla-extract/css'
import vars from '@/styles/variables.css'

export const container = style({
  display: 'flex',
  flexDirection: 'column',
  alignItems: 'center',
  justifyContent: 'center',
  minHeight: '100vh',
})

export const animation = style({
  width: '200px',
  height: '200px',
})

export const waitMsg = style({
  textAlign: 'center',
  fontSize: vars.fontSize.large,
  color: vars.color.black,
  whiteSpace: 'break-spaces',
})
