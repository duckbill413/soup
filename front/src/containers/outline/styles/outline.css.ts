import { globalStyle, style } from '@vanilla-extract/css'
import vars from '@/styles/variables.css'


export const container = style ({
  display:'flex',
  justifyContent:'center'
})

export const mainDivision = style ({
  display: 'flex',
  flexDirection: 'column',
  width: '93%'
})

globalStyle(`${mainDivision} p`, {
  fontWeight:'bold',
  fontSize: '1.3rem',
  marginRight:vars.space.tiny
})
