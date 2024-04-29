import vars from '@/styles/variables.css'
import { style } from '@vanilla-extract/css'

export const dependencies = style({
  flex: 2,

  display: 'flex',
  flexDirection: 'column',
})

export const section = style({
  flex: 1,
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'space-between',
})

export const box = style({
  flex: 1,
  borderRadius: '10px',
  padding: '12px',
  height: '100%',
})

export const whiteBox = style({
  border: `2px solid ${vars.color.gray}`,
  background: 'white',
})

export const greenBox = style({
  background: '#D5E7CC',
})

export const list = style({
  width: '100%',
  padding: '16px 0',
  fontSize: vars.fontSize.caption,
  fontWeight: 600,
})

export const name = style({
  padding: '0 10px',
})

export const white = style({
  borderBottom: `2px solid ${vars.color.gray}`,
  ':hover': {
    background: vars.color.lightGray,
  },
})

export const green = style({
  borderBottom: `2px solid ${vars.color.main}`,
  ':hover': {
    background: vars.color.main,
  },
})
