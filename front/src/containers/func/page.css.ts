import { globalStyle, style } from '@vanilla-extract/css'
import vars from '@/styles/variables.css'

export const tableHeader = style({
  display: 'flex',
  justifyContent: 'space-between',
  marginBottom:  vars.space.tiny
})

export const sortHeader = style({
  height: '40%',
  border: '1px solid black',
  borderRadius: '40px',
  padding: '7px',
  fontSize: vars.fontSize.caption,
  color: vars.color.deepGray,
  cursor: 'pointer',
})

globalStyle(`${tableHeader} p`,{
  margin: `0 ${vars.space.tiny} 0 0`,

});
globalStyle(`${tableHeader} > p`,{
  fontWeight: 500,
});
globalStyle(`${tableHeader} div:nth-child(2)`,{
  cursor: 'pointer',
  marginLeft: vars.space.tiny
});
globalStyle(`${tableHeader} div:nth-child(2)>p`,{
  marginLeft: vars.space.tiny,
  fontWeight: 'bold'
});

globalStyle(`${tableHeader} > div`,{
  display: 'flex',
  alignItems: 'center'
});

