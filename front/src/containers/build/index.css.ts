import vars from '@/styles/variables.css'
import { globalStyle, style } from '@vanilla-extract/css'

// RadioSection

export const section = style({
  display: 'flex',
  justifyContent: 'space-between',

  padding: `0 0 ${vars.space.base}`,
})

export const project = style({
  flex: 2,
})

export const lang = style({
  flex: 2,
})

export const version = style({
  flex: 3,
})

globalStyle(`${section} h4`, {
  fontSize: vars.fontSize.medium,
  fontWeight: 800,
  color: vars.color.black,

  // 기본 margin 설정 변경
  margin: `0 0 16px`,
})

// Metadata

export const metadata = style({
  flex: 1,

  display: 'flex',
  flexDirection: 'column',
  gap: vars.space.base,
})

export const rowRadio = style({
  display: 'flex',
  justifyContent: 'space-between',
  alignItems: 'center',
})

globalStyle(`${rowRadio} span`, {
  fontSize: vars.fontSize.small,
  fontWeight: 600,
})
