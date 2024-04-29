import vars from '@/styles/variables.css'
import { style } from '@vanilla-extract/css'

export const build = style({
  padding: `0 ${vars.space.large}`,
})

export const line = style({
  border: '0',
  height: '0',
  boxShadow: `0 0 3px 1px ${vars.color.lightGray}`,
})

export const row = style({
  display: 'flex',
  justifyContent: 'space-between',
  gap: vars.space.xxLarge,
})
