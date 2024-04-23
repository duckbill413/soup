import { globalStyle, style } from '@vanilla-extract/css'
import vars from '@/styles/variables.css'

export const container = style({
  width: '100vw',
  height: '100vh',
});

globalStyle('body', {
  margin: '0',
});