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
  boxShadow:vars.boxShadow.customOuter
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