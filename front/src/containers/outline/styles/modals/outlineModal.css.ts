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
})

export const middleContainer = style ({
  overflowY:'auto',
  '::-webkit-scrollbar':{
    width: vars.space.tiny,
  },
  '::-webkit-scrollbar-thumb':{
    height: '5%',
    background: '#D3D3D3',
  },
  height:'86%',
})

export const topDivision = style ({
  display:'flex',
  justifyContent:'space-between',
  height:'10%'
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
  display:'flex',
  flexDirection:'column',
})

export const middleSubDiv = style ({
  display: 'flex', flexDirection: 'column'
})

export const mainRoleDiv = style ({
  display: 'flex',
  marginBottom:'3%',
  marginTop:'3%'
})

export const mainInviteDiv = style ({
  display: 'flex',
  justifyContent:'space-evenly'
})

export const middleRoleDiv = style ({
  width:'125px',
  marginLeft:'3%',
  marginRight:'1%'
})

export const middleNameDiv = style ({
  width:'15%',
  display:'flex',
  alignItems:'center'
})

export const middleEmailDiv = style ({
  width:'30%',
  display:'flex',
  alignItems:'center'
})
export const invite = style ({
  width:"15%",
  height:'40px',
  borderRadius:'10px',
  backgroundColor:'#FF7E20',
  boxShadow: '0 4px #C65102',
  color:'white',
  textAlign:'center',
  transition: 'all 0.3s ease',
  ':hover': {
    backgroundColor: '#E76E0A',
  },
  ':active': {
    boxShadow: '0 2px #D15C00',
    transform: 'translateY(3px)'
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
  display:'flex',
  alignItems:'center',
  background:'#DEB06E',
  boxShadow:'rgba(0, 0, 0, 0.24) 0px 3px 3px',
  borderRadius:'5px',
  padding:'0.3rem',
  paddingRight:'0.4rem',
  paddingLeft:'0.4rem',
  fontWeight:'bold',
  margin:'0.3rem'
})

export const deleteButton = style({
  marginLeft:'5px',
  color: '#515455',
  fontSize:'0.8rem'
})

export const invitedRole = style ({
  background:'#B2D1FF',
  borderRadius:'5px',
  boxShadow:'rgba(0, 0, 0, 0.24) 0px 3px 3px',
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