import { keyframes, style } from '@vanilla-extract/css'
import vars from '@/styles/variables.css'

export const gradient = keyframes({
  '0%': { backgroundPosition: '0% 50%' },
  '50%': { backgroundPosition: '100% 50%' },
  '100%': { backgroundPosition: '0% 50%' },
})

export const background = style({
  background: `linear-gradient(-45deg, ${vars.color.cream}, #E3F7CE, ${vars.color.skyBlue})`,
  backgroundSize: '400% 400%',
  animation: `${gradient} 7s ease infinite`,
})
