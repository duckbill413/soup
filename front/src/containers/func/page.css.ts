import { globalStyle, style } from '@vanilla-extract/css'
import vars from '@/styles/variables.css'

export const tableHeader = style({
  display: 'flex',
  justifyContent: 'space-between',
  marginBottom:  vars.space.base
})

export const sortHeader = style({
  borderRadius: '30px',
  border: `1px solid ${vars.color.deepGray}`,
  background: 'white',
  fontFamily: 'inherit',
  color: vars.color.deepGray,

  display: 'flex',
  gap: vars.space.tiny,
  alignItems: 'center',
  padding: `${vars.space.tiny} ${vars.space.small}`,

  cursor: 'pointer',

  ':hover': {
    background: vars.color.black,
    color: 'white',
  },
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

