import { globalStyle, style } from '@vanilla-extract/css'
import vars from '@/styles/variables.css'

export const container = style({
  height: vars.space.large,
  width: '100%',
  position: 'fixed',
  display: 'flex',
  alignItems: 'center',
  backgroundColor: vars.backgroundColor.black,
  padding: vars.space.tiny,
  color: 'white',
  boxShadow: `0px 0px 5px black`,
});

globalStyle(`${container} > p`, {
  paddingLeft: vars.space.base,
});