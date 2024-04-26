import { globalStyle, style } from '@vanilla-extract/css'

export const container = style({
  width: '100vw',
  height: '100vh',
})

// TODO: export 에러 잠시 수정하려고 추가. 삭제 해도 됨
export const temp = style({})

globalStyle('body', {
  margin: '0',
})

globalStyle('button', {
  border: 'none',
  padding: 0,
  margin: 0,
  textAlign: 'start',
  font: 'inherit',
  color: 'inherit',
  background: 'none',
  cursor: 'pointer',
})

globalStyle('input', {
  fontFamily: 'inherit',
})

globalStyle('textarea', {
  fontFamily: 'inherit',
})

globalStyle('button', {
  fontFamily: 'inherit',
})

globalStyle('select', {
  fontFamily: 'inherit',
})
