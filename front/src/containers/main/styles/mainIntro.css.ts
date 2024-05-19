import { style } from '@vanilla-extract/css'
import vars from '@/styles/variables.css'

export const container = style({
  height: '90vh',
  width: '100%',
  display: 'flex',
  flexDirection: 'column',
  alignItems: 'center',
})

export const mainDiv = style({
  height: '100%',
  display: 'flex',
  flexDirection: 'column',
  justifyContent: 'center',
  alignItems: 'center',
})

export const title = style({
  margin: vars.space.tiny,
  fontSize: '3.5rem',
  fontStyle: 'italic',
})

export const subTitle = style({
  margin: vars.space.tiny,
  fontSize: '1.3rem',
})

export const subDiv = style({
  display: 'flex',
  justifyContent: 'space-between',
})
