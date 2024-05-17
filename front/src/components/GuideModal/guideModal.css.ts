import vars from '@/styles/variables.css'
import { style } from '@vanilla-extract/css'

export const draggable = style({
  position: 'fixed',
  zIndex: 1000,
  top: 160,
  right: 30,
  width: '100%',
  display: 'flex',
  alignItems: 'flex-start',
  justifyContent: 'flex-end',
  cursor: 'move',
})

export const modal = style({
  width: 'fit-contents',
  minWidth: '40%',
  maxWidth: '80%',
  position: 'absolute',
  background: 'white',
  boxShadow: '0 4px 8px rgba(0,0,0,0.3)',
})

export const header = style({
  display: 'flex',
  justifyContent: 'space-between',
  padding: 20,
})

export const title = style({
  display: 'inline-block',
  fontWeight: 700,
})

export const body = style({
  padding: '0 20px',
  maxHeight: '65vh',
  overflowY: 'auto',
  '::-webkit-scrollbar': {
    width: vars.space.tiny,
  },
  '::-webkit-scrollbar-thumb': {
    height: '5%',
    background: '#D3D3D3',
  },
})
