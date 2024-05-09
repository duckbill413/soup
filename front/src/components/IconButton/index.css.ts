import vars from '@/styles/variables.css'
import { style } from '@vanilla-extract/css'

export const iconButton = style({
  borderRadius: '30px',
  border: `1px solid ${vars.color.deepGray}`,
  background: 'white',
  fontFamily: 'inherit',
  color: vars.color.deepGray,

  display: 'flex',
  gap: vars.space.tiny,
  alignItems: 'center',
  padding: `${vars.space.tiny} ${vars.space.small}`,

  cursor: 'pointer',

  ':hover': {
    background: vars.color.black,
    color: 'white',
  },
})

export const buttonName = style({
  color: 'inherit',
  fontSize: vars.fontSize.small,
  fontFamily: 'inherit',
  fontWeight: 500,
})

export const imgColor = style({
  color: 'inherit',

  ':hover': {
    color: 'white',
  },
})
