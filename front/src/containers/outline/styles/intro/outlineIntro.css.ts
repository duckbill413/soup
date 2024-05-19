import { style } from '@vanilla-extract/css'
import vars from '@/styles/variables.css'

export const container = style ({
  display: 'flex'
})

export const photoDivision = style ({
  display: 'flex',
  flexDirection: 'column'
})

export const introDivision = style ({
  display: 'flex',
  flexDirection: 'column',
  marginLeft: vars.space.large,
  width: '50%'
})

export const img = style ({
  borderRadius: '10px'
})