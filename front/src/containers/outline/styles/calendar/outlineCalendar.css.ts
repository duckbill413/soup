import { style } from '@vanilla-extract/css';

export const container = style ({
  display: 'flex',
  flexDirection: 'column',
  alignItems: 'flex-start'
})

export const division = style ({
  display: 'flex',
  width: '50%',
  justifyContent: 'space-between'
})