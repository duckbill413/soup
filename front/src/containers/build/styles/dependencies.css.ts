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
  flex: '1 0 0',
  borderRadius: '10px',
  padding: '12px',
  maxHeight: '400px',
  minHeight: '100%',
  overflowY: 'auto',

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

  display: 'flex',
  justifyContent: 'space-between',
  alignItems: 'center',

  color: vars.color.deepGray,
})

export const name = style({
  display: 'inline-block',
  padding: '0 10px',
  wordBreak: 'break-all',

  color: vars.color.black,
  fontSize: vars.fontSize.caption,
  fontWeight: 600,
})

export const desc = style({
  display: 'block',
  padding: '2px 10px',
  wordBreak: 'break-all',

  color: vars.color.deepGray,
  fontSize: vars.fontSize.caption,
  fontWeight: 600,
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
