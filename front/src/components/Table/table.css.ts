import vars from '@/styles/variables.css'
import { globalStyle, style } from '@vanilla-extract/css'

export const essential = style({
  fontSize: vars.fontSize.medium,
  color: 'red',
})

export const table = style({
  tableLayout: 'fixed',
  width: '100%',
  wordBreak: 'break-all',
  borderCollapse: 'collapse',
})

globalStyle(`${table} thead`, {
  width: '100%',
})

globalStyle(`${table} tr>th:first-of-type`, {
  borderRadius: '8px 0 0 0',
  borderLeft: 'none',
})

globalStyle(`${table} tr>th:last-of-type`, {
  borderRadius: '0 8px 0 0',
  borderRight: 'none',
})

globalStyle(`${table} tbody>tr`, {
  position: 'relative',
})

globalStyle(`${table} tbody>tr:hover`, {
  background: 'whitesmoke',
})

globalStyle(`${table} th`, {
  fontSize: vars.fontSize.small,
  fontWeight: 500,
  color: vars.color.black,
  background: vars.color.gray,
  borderInline: '2px solid white',
  padding: '6px 0',
})

globalStyle(`${table} td`, {
  fontSize: vars.fontSize.caption,
  fontWeight: 500,
  color: vars.color.black,

  textAlign: 'center',
  padding: '12px 0',
  borderBottom: `2px solid ${vars.color.gray}`,
})

export const deleteButton = style({})

globalStyle(`${table} ${deleteButton}`, {
  position: 'absolute',
  right: 4,
  top: 14,
  fontSize: vars.fontSize.caption,
  fontWeight: 800,
  color: 'white',

  width: 'fit-contents',
  padding: '6px',
  borderRadius: '50%',
  backgroundColor: vars.color.orange,
  display: 'flex',
  justifyContent: 'center',
  alignItems: 'center',
  aspectRatio: '1 / 1',
})

globalStyle(`${table} tr .newLine`, {
  paddingInline: vars.space.base,
  textAlign: 'start',
  color: vars.color.deepGray,
  cursor: 'pointer',
})
