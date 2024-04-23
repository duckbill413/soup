import { globalStyle, style } from '@vanilla-extract/css'
import vars from '@/styles/variables.css'

export const container = style({
  height: '100%',
  width: '5%',
  position: 'fixed',
  display: 'flex',
  flexDirection: 'column',

  alignItems: 'center',
  backgroundColor: vars.backgroundColor.black,
  padding: `${vars.space.xLarge} ${vars.space.tiny} ${vars.space.tiny} ${vars.space.tiny}`,

  color: 'white',
});
