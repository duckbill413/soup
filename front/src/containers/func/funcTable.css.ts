import { globalStyle, style } from '@vanilla-extract/css'
import vars from '@/styles/variables.css'

export const container = style({

  height:'64vh',
});

export const table = style({
  tableLayout: 'fixed',
  width: '100%',
  wordBreak: 'break-all',
  borderCollapse: 'collapse',
});

export const manager = style({
  display: 'flex',
  justifyContent: 'center',
  alignItems: 'center'
})



globalStyle(`${manager}>p`, {
  margin: `0 ${vars.space.tiny} 0 0`
})

globalStyle(`${manager}>img`, {
  borderRadius: '50%',
  aspectRatio: 1
})


globalStyle(`${table}>thead>tr>th:first-of-type`, {
  borderRadius: '8px 0 0 0',
  borderLeft: 'none',
})

globalStyle(`${table}>thead>tr>th:last-of-type`, {
  borderRadius: '0 8px 0 0',
  borderRight: 'none',
})

export const createNew = style({
  selectors: {
    '&:hover': {
    background: 'whitesmoke',
    }
  },
  cursor: 'pointer',
})

globalStyle(`${table}>tbody>tr:last-of-type>td`, {
  paddingInline: vars.space.base,
  textAlign: 'start',
  color: vars.color.deepGray,
})

globalStyle(`${table}>thead>tr>th`, {
  fontSize: vars.fontSize.small,
  fontWeight: 500,
  color: vars.color.black,
  background: vars.color.gray,
  borderInline: '2px solid white',
  padding: '6px 0',
})


const thStyles  = ['15%', '15%', '35%', '10%', '10%', '15%'];

thStyles.forEach((w, index) => {
  globalStyle(`${table} > thead > tr > th:nth-child(${index + 1})`, {
    width: w,
    fontWeight: 'bold',
  });
});
globalStyle(`${table} > tbody > tr > td:nth-child(1)`, {
  fontWeight: 'bold',
});



globalStyle(`${table}>tbody>tr>td`, {
  fontSize: vars.fontSize.caption,
  color: vars.color.black,
  padding: '12px 12px',
  borderBottom: `2px solid ${vars.color.gray}`,
  fontWeight: 500,
  textAlign: 'center',

})

globalStyle(`${table} > tbody > tr > td input:focus`,{
  outline: 'none',
});
globalStyle(`${table} > tbody > tr > td > input`,{
  border: 'none',
  cursor: 'pointer',
  fontWeight: 600,
  textAlign: 'center',
});

globalStyle(`${table} > tbody > tr > td input`,{
  width: '100%',
  height: '100%'
});



