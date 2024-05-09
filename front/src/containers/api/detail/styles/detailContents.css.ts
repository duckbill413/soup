import { table } from '@/components/Table/table.css'
import vars from '@/styles/variables.css'
import { globalStyle, style } from '@vanilla-extract/css'

export const backButton = style({
  fontSize: vars.fontSize.medium,
  color: vars.color.black,
  textDecoration: 'none',

  width: 'fit-content',
  display: 'flex',
  alignItems: 'center',
  padding: `${vars.space.tiny} ${vars.space.small}`,

  borderRadius: '30px',
  transition: 'all 0.5s ease',

  ':hover': {
    background: vars.color.gray,
  },
})

export const buttonName = style({
  fontFamily: 'inherit',
  fontWeight: 800,
  lineHeight: '24px',
})

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
