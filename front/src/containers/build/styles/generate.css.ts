import vars from '@/styles/variables.css'
import { style } from '@vanilla-extract/css'

export const container = style({
  display: 'flex',
  justifyContent: 'center',
  margin: `${vars.space.large} 0 ${vars.space.base}`,
})

export const section = style({
  padding: vars.space.base,
  display: 'flex',
  gap: vars.space.small,
})

export const box = style({
  flex: '1 0 0',
  borderRadius: '10px',
  padding: '12px',
  maxHeight: '80vh',
  overflowY: 'auto',

  border: `2px solid ${vars.color.gray}`,
  background: 'white',

  '::-webkit-scrollbar': {
    width: vars.space.tiny,
  },

  '::-webkit-scrollbar-thumb': {
    height: '5%',
    background: '#D3D3D3',
    backgroundClip: 'padding-box',
    borderWidth: '5px 2px',
    borderStyle: 'solid',
    borderColor: 'transparent',
    borderRadius: '15px',
  },
})

export const file = style({
  flex: 1,
})

export const code = style({
  flex: 3,
})

export const list = style({
  padding: '16px 8px',

  display: 'flex',
  alignItems: 'center',

  color: vars.color.deepGray,

  borderBottom: `2px solid ${vars.color.gray}`,
  ':hover': {
    background: vars.color.lightGray,
  },
})

export const name = style({
  display: 'inline-block',
  padding: '0 10px',
  wordBreak: 'break-all',

  color: vars.color.black,
  fontSize: vars.fontSize.caption,
  fontWeight: 600,
})
