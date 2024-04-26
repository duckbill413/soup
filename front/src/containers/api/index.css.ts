import vars from '@/styles/variables.css'
import { style } from '@vanilla-extract/css'

export const buttonGroup = style({
  display: 'flex',
  justifyContent: 'space-between',
  alignItems: 'center',

  paddingBottom: vars.space.small,
})

export const right = style({
  display: 'flex',
  alignItems: 'center',
  gap: vars.space.tiny,
})
