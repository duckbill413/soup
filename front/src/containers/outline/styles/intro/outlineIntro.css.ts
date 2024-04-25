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

export const input = style ({
  padding: vars.space.small,
  borderRadius: '5px',
  border: '1px solid black'
})

export const textarea = style ({
  padding: vars.space.small,
  borderRadius: '5px',
  height: '30%'
})

export const img = style ({
  borderRadius: '10px'
})