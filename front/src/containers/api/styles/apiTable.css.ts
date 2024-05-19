import vars from '@/styles/variables.css'
import { globalStyle, style } from '@vanilla-extract/css'

export const method = style({
  width: 'fit-content',
  borderRadius: '30px',
  background: vars.color.orange,
  padding: '4px 8px',
  color: 'white',
})

globalStyle(`${method}.GET`, {
  background: '#8DBC5F',
})

globalStyle(`${method}.POST`, {
  background: '#DC9C80',
})

globalStyle(`${method}.PUT`, {
  background: '#DEB06E',
})

globalStyle(`${method}.PATCH`, {
  background: '#93B5E7',
})

globalStyle(`${method}.DELETE`, {
  background: '#D79EBA',
})

export const link = style({
  color: vars.color.black,
})
