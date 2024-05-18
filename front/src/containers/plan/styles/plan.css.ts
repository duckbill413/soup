import { globalStyle, style } from '@vanilla-extract/css'
import vars from '@/styles/variables.css'

export const container = style ({
  display: 'flex',
  justifyContent: 'center',
  height:'100%'
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
  height:'40px',
  width:'37%',
  textAlign: 'center',
  backgroundColor: '#FF7E20',
  borderRadius:'15px',
  fontWeight:'bold',
  color: '#ffffff',
  transition: 'all 0.3s ease',
  boxShadow: '0 4px #C65102',
  ':hover': {
    backgroundColor: '#E76E0A',
  },
  ':active': {
    boxShadow: '0 2px #D15C00',
    transform: 'translateY(3px)'
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
  display:'flex',
  alignItems:'center',
  backgroundColor: '#E0DFDF',
  boxShadow:'rgba(0, 0, 0, 0.24) 0px 3px 3px',
  borderRadius: '6px',
  margin: '0.23rem',
  padding: '0.2rem'
})

export const tagDivision = style ({
  paddingRight: '0.3em'
})

export const buttonDiv = style ({
  height: '6%',
  display: 'flex',
  justifyContent: 'center',
  marginTop: vars.space.large,
})

export const deleteButton = style ({
  color: '#515455',
  fontSize:'0.8rem'
})

export const input = style ({
  height:'4%',
  marginBottom:'1.3rem',
  padding:vars.space.tiny,
  paddingLeft:'3%',
  backgroundColor:'#F4F4F4',
  boxShadow:vars.boxShadow.customInner,
  border:'none',
  borderRadius:'10px',
})

export const afterAI = style ({
  display: 'flex',
  flexDirection: 'column',
  width: '40%'
})

globalStyle(`${mainDivision} p`, {
  fontWeight:'bold',
  fontSize: '1.3rem',
  marginRight:vars.space.tiny,
})

globalStyle(`${mainDivision} textarea`, {
  height:'10%',
  padding:vars.space.tiny
})