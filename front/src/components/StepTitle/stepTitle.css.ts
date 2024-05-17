import vars from '@/styles/variables.css'
import { style } from '@vanilla-extract/css'

export const container = style({
  paddingBottom: vars.space.large,

  display: 'flex',
  justifyContent: 'space-between',
  alignItems: 'flex-end',
  position: 'relative',
})

export const stepNum = style({
  fontSize: vars.fontSize.medium,
  color: '#797979',
  display: 'block',
})

export const title = style({
  fontSize: vars.fontSize.t4,
  color: vars.color.black,
  fontWeight: 800,
})

export const desc = style({
  fontSize: vars.fontSize.caption,
  color: '#797979',
  fontWeight: 500,
})

export const guideTitle = style({
  fontFamily: 'inherit',
  fontSize: vars.fontSize.caption,
  color: vars.color.deepGray,
  fontWeight: 500,

  display: 'flex',
  alignItems: 'center',
  gap: vars.space.tiny,

  background: 'none',
  border: 'none',
  outline: 'none',

  cursor: 'pointer',
})
