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
  height:'30%',
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
  fontWeight:'bold',
  margin:'0.5rem',
  marginLeft:'3%'
})

export const topSubXDiv = style ({
  display:'flex',
  alignItems:'center',
  marginRight:'3%'
})

export const input = style ({
  width:'80%',
  padding:vars.space.small,
  backgroundColor:'#F4F4F4',
  boxShadow:vars.boxShadow.customInner,
  border:'none',
  borderRadius:'10px',
})

export const button = style ({
  padding: '10px 20px',
  backgroundColor: '#3498db',
  border: 'none',
  color: 'white',
  fontSize: '16px',
  outline: 'none',
  boxShadow: '0 6px #2980b9',
  marginTop: '5%',
  marginRight: '4%',
  transition: 'all 0.3s ease',
  borderRadius: '10px',
  ':active': {
    boxShadow: '0 2px #2980b9',
    transform: 'translateY(4px)'
  }
})