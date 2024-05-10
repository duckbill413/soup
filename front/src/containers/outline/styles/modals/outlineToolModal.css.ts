import { style } from '@vanilla-extract/css'
import vars from '@/styles/variables.css'

export const modalContainer = style ({
  zIndex: 101,
  position: 'fixed',
  top: 0,
  left: 0,
  width: '100%',
  height: '100%',
  display: 'flex',
  justifyContent: 'center',
  alignItems: 'center',
  backgroundColor: 'rgba(0, 0, 0, 0.4)',
})

export const modalSubContainer = style ({
  backgroundColor:'white',
  borderRadius:'10px',
  width:'30%',
  height:'40%',
  overflowY:'auto',
  '::-webkit-scrollbar':{
    width: vars.space.tiny,
  },
  '::-webkit-scrollbar-thumb':{
    height: '5%',
    background: '#D3D3D3',
  },
})

export const topDivision = style ({
  display:'flex',
  justifyContent:'space-between'
})

export const topSubTitle = style ({
  margin:'0.5rem',
  marginLeft:'3%'
})

export const topSubXDiv = style ({
  display:'flex',
  alignItems:'center',
  marginRight:'3%'
})

export const button = style({
  width:"30%",
  borderRadius:'10px',
  backgroundColor:'#FF7E20',
  color:'white',
  textAlign:'center',
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

export const toolInput = style ({
  width:'47%',
  height:'12%',
  padding:'0.1rem',
  paddingLeft:'0.8rem',
  border:'none',
  borderRadius:'5px',
  borderBottom:'1px solid #F4F4F4',
  boxShadow:vars.boxShadow.customInner,
  backgroundColor:'#F4F4F4', outline: 'none'
})

export const urlInput = style ({
  width:'90%',
  height:'12%',
  padding:'0.1rem',
  paddingLeft:'0.8rem',
  border:'none',
  borderRadius:'5px',
  borderBottom:'1px solid #F4F4F4',
  boxShadow:vars.boxShadow.customInner,
  backgroundColor:'#F4F4F4', outline: 'none'
})

export const inputMainDiv = style ({
  width:'95%',
  height:'80%',
  display:'flex',
  flexDirection:'column',
  justifyContent : 'space-evenly',
  marginLeft:'5%',

})

export const inputSubDiv = style ({
  height:'15%',
  display:'flex',
  justifyContent:'center',
  marginTop:'10%'
})