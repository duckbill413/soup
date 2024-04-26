import vars from '@/styles/variables.css'
import { globalStyle, style } from '@vanilla-extract/css'
import { container } from '@/containers/outline/styles/outline.css'

globalStyle(`${container} table`, {
  width: '80%'
})

globalStyle(`${container} td`, {
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
