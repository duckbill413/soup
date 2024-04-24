import vars from '@/styles/variables.css'
import { globalStyle } from '@vanilla-extract/css'

globalStyle('table', {
  tableLayout: 'fixed',
  width: '100%',
  wordBreak: 'break-all',
  borderCollapse: 'collapse',
})

globalStyle('thead', {
  width: '100%',
})

globalStyle('tr>th:first-of-type', {
  borderRadius: '8px 0 0 0',
  borderLeft: 'none',
})

globalStyle('tr>th:last-of-type', {
  borderRadius: '0 8px 0 0',
  borderRight: 'none',
})

globalStyle('tbody>tr:hover', {
  background: 'whitesmoke',
  cursor: 'pointer',
})

globalStyle('tbody>tr:last-of-type>td', {
  paddingInline: vars.space.base,
  textAlign: 'start',
  color: vars.color.deepGray,
})

globalStyle('th', {
  fontSize: vars.fontSize.small,
  fontWeight: 500,
  color: vars.color.black,
  background: vars.color.gray,
  borderInline: '2px solid white',
  padding: '6px 0',
})

globalStyle('td', {
  fontSize: vars.fontSize.caption,
  fontWeight: 500,
  color: vars.color.black,

  textAlign: 'center',
  padding: '12px 0',
  borderBottom: `2px solid ${vars.color.gray}`,
})
