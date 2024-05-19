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
  boxShadow: '0 6px #C65102',
  color:'white',
  textAlign:'center',
  transition: 'all 0.3s ease',
  ':hover': {
    backgroundColor: '#E76E0A',
  },
  ':active': {
    boxShadow: '0 2px #D15C00',
    transform: 'translateY(5px)'
  }
})

export const inputMainDiv = style ({
  width:'95%',
  height:'70%',
  display:'flex',
  flexDirection:'column',
  justifyContent : 'space-evenly',
  marginLeft:'5%',

})

export const inputSubDiv = style ({
  height:'18%',
  display:'flex',
  justifyContent:'center',
})