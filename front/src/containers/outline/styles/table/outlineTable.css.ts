import { globalStyle, style } from '@vanilla-extract/css'
import vars from '@/styles/variables.css'

globalStyle('table', {
  width: '80%'
})

globalStyle('td', {
  padding:'0.3rem',
  borderBottom: '1px solid #D9D9D9',
  textAlign:'center'
})

export const tableToolTitle = style ({
  background:'#D9D9D9',
  width:'20%',
  padding:vars.space.tiny,
  borderRadius:'5px'
})

export const tableURLTitle = style ({
  background:'#D9D9D9',
  borderRadius:'5px'
})
