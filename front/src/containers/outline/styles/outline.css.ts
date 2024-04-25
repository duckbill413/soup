import { style } from '@vanilla-extract/css';
import vars from '@/styles/variables.css'

export const boldText = style ({
  fontWeight:'bold',
  fontSize: '1.3rem',
  marginRight:vars.space.tiny
})