import { globalStyle, style } from '@vanilla-extract/css'
import vars from '@/styles/variables.css'

export const container = style({
  height: '5%',
  width: '100%',
  position: 'fixed',
  display: 'flex',
  alignItems: 'center',
  backgroundColor: vars.backgroundColor.black,
  padding: vars.space.tiny,
  color: 'white',
});

globalStyle(`${container} > p`, {
  paddingLeft: vars.space.base,
});