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
  justifyContent: 'space-between',
})

export const box = style({
  flex: 1,
  borderRadius: '10px',
  padding: '12px',
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
  fontWeight: 600,
})

export const white = style({
  borderBottom: `2px solid ${vars.color.gray}`,
})

export const green = style({
  borderBottom: `2px solid ${vars.color.main}`,
})
