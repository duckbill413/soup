import { globalStyle, style } from '@vanilla-extract/css'
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
  width:'45%',
  height:'60%',
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

export const middleDiv = style ({
  width:'100%',
  height:'15%',
  display:'flex',
  justifyContent:'space-around',
  alignItems:'center'
})

export const middleSubDiv = style ({
  display: 'flex', flexDirection: 'column'
})

export const roleInput = style ({
  width:'18%',
  height:'50%',
  padding:'0.1rem',
  paddingLeft:'0.6rem',
  border:'none',
  borderRadius:'5px',
  borderBottom:'1px solid #F4F4F4',
  boxShadow:vars.boxShadow.customInner,
  outline: 'none'
})

export const nameInput = style({
  width:'10%',
  height:'50%',
  padding:'0.1rem',
  paddingLeft:'0.6rem',
  border:'none',
  borderRadius:'5px',
  borderBottom:'1px solid #F4F4F4',
  boxShadow:vars.boxShadow.customInner,
  backgroundColor:'#F4F4F4', outline: 'none'
})

export const emailInput = style({
  width:'30%',
  height:'50%',
  padding:'0.1rem',
  paddingLeft:'0.6rem',
  border:'none',
  borderRadius:'5px',
  borderBottom:'1px solid #F4F4F4',
  boxShadow:vars.boxShadow.customInner,
  backgroundColor:'#F4F4F4', outline: 'none'
})

export const invite = style ({
  width:"15%",
  height:'50%',
  borderRadius:'15px',
  backgroundColor:'#FF7E20',
  color:'white',
  textAlign:'center',
  boxShadow:vars.boxShadow.customOuter,
  ':hover': {
    transform: 'scale(1.05)',
    transitionDuration: '300ms',
  },
  ':active': {
    transform: 'scale(0.90)',
    transitionDuration: '300ms'
  }
})

export const nowTeamTitle = style ({
  marginLeft:'3%',
  fontSize:'1.1rem'
})

export const bottomDivision = style ({
  display:'flex',
  justifyContent:'space-between',
  margin:'2rem'
})

export const bottomProfile = style ({
  display:'flex'
})

export const profileImg = style ({
  borderRadius:'50%'
})

export const profileDetail = style ({
  display:'flex',
  flexDirection:'column',
  justifyContent:'center',
  marginLeft:'8%'
})

export const roleSubDiv = style ({
  display:'flex',
  alignItems:'center'
})

export const role = style ({
  background:'#DEB06E',
  borderRadius:'5px',
  padding:'0.3rem',
  fontWeight:'bold',
  margin:'0.3rem'
})

export const invitedRole = style ({
  background:'#B2D1FF',
  borderRadius:'5px',
  padding:'0.3rem',
  fontWeight:'bold',
  margin:'0.3rem'
})


export const roleShow = style ({
  display: 'flex',
  flexWrap: 'wrap'
})

globalStyle(`${profileDetail} p`,{
  fontWeight:'normal',
  fontSize:'1rem',
  margin:'0'
})