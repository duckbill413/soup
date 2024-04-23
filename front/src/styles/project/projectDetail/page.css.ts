import { style } from '@vanilla-extract/css'
import vars from '@/styles/variables.css'

export const container = style({
  width: '100%',
  height: '100%',
});


export const content = style({
  padding: `${vars.space.large} 0 0 ${vars.space.xLarge}`
});