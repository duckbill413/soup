import { table } from '@/components/Table/table.css'
import vars from '@/styles/variables.css'
import { globalStyle, keyframes, style } from '@vanilla-extract/css'

export const inputSection = style({
  padding: `${vars.space.large} ${vars.space.xLarge}`,
  display: 'flex',
  flexDirection: 'column',
  gap: vars.space.base,
})

globalStyle(`${table} td input`, {
  width: '90%',
  outline: 'none',
  border: 'none',
  backgroundColor: 'inherit',

  fontSize: vars.fontSize.caption,
  fontWeight: 500,
  color: vars.color.black,
  textAlign: 'center',
  wordBreak: 'break-all',
})

globalStyle(`${table} td textarea`, {
  width: '90%',
  outline: 'none',
  border: 'none',
  backgroundColor: 'inherit',
  resize: 'none',

  fontSize: vars.fontSize.caption,
  fontWeight: 500,
  color: vars.color.black,
  textAlign: 'center',
  wordBreak: 'break-all',
})

export const easeAnimation = keyframes({
  from: {
    opacity: '0',
    transform: 'translateY(-5%)',
  },
  to: {
    opacity: '1',
    transform: 'translateY(0)',
  },
})

export const requestBody = style({
  animation: `${easeAnimation} 0.5s ease`,
})

export const badgeAndError = style({
  display: 'flex',
  alignItems: 'center',
  gap: vars.space.small,
})

export const errorMsg = style({
  color: vars.color.orange,
  fontSize: vars.fontSize.caption,
  fontWeight: 700,
})
