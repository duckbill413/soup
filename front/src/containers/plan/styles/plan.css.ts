import { globalStyle, style } from '@vanilla-extract/css'
import vars from '@/styles/variables.css'

export const container = style ({
  display: 'flex',
  justifyContent: 'center',
  height:'100vh'
})

export const mainDivision = style ({
  display: 'flex',
  width: '93%',
  justifyContent: 'space-between'
})

export const illustDivision = style ({
  display:'flex',
  justifyContent:'center',
  marginTop:'25%'
})

export const button = style ({
  width:'40%',
  textAlign: 'center',
  backgroundColor: '#FF7E20',
  borderRadius:'15px',
  fontWeight:'bold',
  color: '#ffffff',
  boxShadow:vars.boxShadow.customOuter,
  ':hover': {
    transform: 'scale(1.10)',
    transitionDuration: '300ms',
  },
  ':active': {
    transform: 'scale(0.90)',
    transitionDuration: '300ms'
  }
})

export const beforeAIContainer = style ({
  display: 'flex',
  flexDirection: 'column',
  width: '42%',
  height: '100%'
})

export const beforeAIMainDiv = style ({
  display: 'flex',
  flexWrap: 'wrap',
  margin: '0.3rem'
})

export const tagContainer = style ({
  backgroundColor: '#9FE0FC',
  borderRadius: '6px',
  margin: '0.1rem',
  padding: '0.2rem'
})

export const tagDivision = style ({
  paddingRight: '0.3em'
})

export const buttonDiv = style ({
  height: '6%',
  display: 'flex',
  justifyContent: 'center',
  marginTop: vars.space.large
})

globalStyle(`${mainDivision} p`, {
  fontWeight:'bold',
  fontSize: '1.3rem',
  marginRight:vars.space.tiny,
})

globalStyle(`${mainDivision} input`, {
  height:'4%',
  marginBottom:'1.3rem',
  padding:vars.space.tiny,
  paddingLeft:'3%',
  backgroundColor:'#F4F4F4',
  boxShadow:vars.boxShadow.customInner,
  border:'none',
  borderRadius:'10px',
})

globalStyle(`${mainDivision} input:focus`, {
  outline: 'none'
})

globalStyle(`${mainDivision} textarea`, {
  height:'10%',
  padding:vars.space.tiny
})