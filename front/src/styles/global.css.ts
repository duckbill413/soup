import { globalStyle, style } from '@vanilla-extract/css'

export const container = style({
  width: '100vw',
  height: '100vh',
});

globalStyle('body', {
  margin: '0',
});

globalStyle('button', {
  border: 'none',
  padding: 0,
  margin: 0,
  textAlign: 'start',
  font: 'inherit',
  color: 'inherit',
  background: 'none',
  cursor: 'pointer'
});
