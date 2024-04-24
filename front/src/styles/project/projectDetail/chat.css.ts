import {  style, } from '@vanilla-extract/css'
import vars from '@/styles/variables.css'


// ChatSVG
export const chat = style({
  position: 'absolute',
  right: 0,
  bottom: 0,
  margin: vars.space.small,
  cursor: 'pointer',

})

