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

export const tdMemberName = style ({
  display:'flex',
  justifyContent:'center',
  alignItems:'center'
})

export const tdMemberImage = style ({
  borderRadius:'50%',
  marginRight:'10%',
})

export const tdDetail = style({
  backgroundColor:'#DEB06E',
  padding:'0.9%',
  paddingRight:'3%',
  paddingLeft:'3%',
  marginRight:'2%',
  borderRadius:'7px',
  fontWeight:'bold'
})

export const toolInput = style ({
  fontSize:'1rem',
  textAlign:'center',
  padding:'0.2rem',
})

export const saveDiv = style ({
  display: 'flex',
  justifyContent: 'flex-start',
  alignItems: 'center',
  width: '100%'
})

export const saveInput = style ({
  flexGrow: 1,
  fontSize:'1rem',
  textAlign:'center',
  marginRight: '3%',
  padding:'0.2rem',
})

export const saveButton = style ({
  marginLeft: 'auto'
})

export const urlMainDiv = style({
  display:'flex',
  justifyContent:'space-between',
  alignItems: 'center'
})

export const urlURLDiv = style ({
  display:'flex',
  justifyContent:'center',
  width:'100%',
})

export const urlIconDiv = style ({
  display:'flex',
  justifyContent:'flex-end'
})

export const aTag = style ({
  textAlign: 'center'
})

export const editButton = style ({
  marginLeft: 'auto',
  marginRight: '3%'
})