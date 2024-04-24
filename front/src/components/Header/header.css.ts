import { globalStyle, style } from '@vanilla-extract/css'
import vars from '@/styles/variables.css'

export const container = style({
  height: vars.space.large,
  width: '99%',
  position: 'fixed',
  display: 'flex',
  justifyContent: 'space-between',
  backgroundColor: vars.backgroundColor.black,
  padding: vars.space.tiny,
  color: 'white',
  boxShadow: `0px 0px 5px black`,
});

export const assistant = style({
  display: 'flex',
  justifyContent: 'end',
});

globalStyle(`${container} > div`, {
  width: '400px',
  height: '100%',
  display: 'flex',
  alignItems: 'center',

});
globalStyle(`${container} > div > p`, {
  marginLeft: vars.space.xLarge,

});

globalStyle(`${assistant} > *`, {
  marginRight: vars.space.small,
  width: '12%',
  aspectRatio: 1,
  borderRadius: '50%',
  cursor: 'pointer'
});