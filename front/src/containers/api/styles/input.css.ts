import vars from '@/styles/variables.css'
import { style } from '@vanilla-extract/css'

export const container = style({
  display: 'flex',
  alignItems: 'center',
})

export const name = style({
  width: '15%',
  minWidth: '200px',
})

export const title = style({
  fontSize: vars.fontSize.medium,
  fontWeight: 700,
})

export const essential = style({
  fontSize: vars.fontSize.medium,
  color: 'red',
})

export const input = style({
  width: 'fit-content',
  minWidth: '500px',

  fontSize: vars.fontSize.small,
  fontFamily: 'inherit',
  color: vars.color.black,
  fontWeight: 500,

  '::placeholder': {
    color: vars.color.deepGray,
    fontSize: vars.fontSize.small,
  },

  ':focus': {
    outline: 'none',
    border: `2px solid ${vars.color.main}`,
    boxShadow: `0 0 8px ${vars.color.main}`,
  },
})
