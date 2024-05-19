import { style } from '@vanilla-extract/css'

export const container = style({
  pointerEvents: 'none',
  position: 'absolute',
  left: 0,
  top: 0,
})

export const division = style({
  position: 'absolute',
  left: '0.5rem',
  top: '1rem',
  borderRadius: '1.5rem',
  padding: '0.2rem 1rem',
})

export const pTag = style({
  whiteSpace: 'nowrap',
  fontSize: '0.875rem',
  lineHeight: 0.1,
  color: '#ffffff',
})

export const cursorChatContainer = style({
  position: 'absolute',
  top: 0,
  left: 0,
})

export const cursorChatDiv = style({
  position: 'absolute',
  left: '0.5rem',
  top: '1.25rem',
  backgroundColor: '#60a5fa',
  padding: '0.5rem 1rem',
  fontSize: '0.875rem',
  lineHeight: '1.625',
  color: '#ffffff',
  borderRadius: 20,
})

export const cursorChatInput = style({
  zIndex: 10,
  width: '15rem',
  border: 'none',
  backgroundColor: 'transparent',
  color: '#ffffff',
  '::placeholder': {
    color: '#93c5fd',
  },
  ':focus': {
    outline: 'none',
  },
})

export const cursorMain = style({
  position: 'relative',
  width: '100%',
  height: '100%',
})
