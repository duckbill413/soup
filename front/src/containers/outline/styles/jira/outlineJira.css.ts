import vars from '@/styles/variables.css';
import { style, globalStyle } from '@vanilla-extract/css';

export const toggleSwitch = style({
  width: '60px',
  height: '30px',
  backgroundColor: '#ccc',
  borderRadius: '15px',
  position: 'relative',
  cursor: 'pointer',
  marginLeft: '10px'
});

export const slider = style({
  width: '30px',
  height: '30px',
  backgroundColor: '#533C00',
  borderRadius: '50%',
  position: 'absolute',
  top: 0,
  transition: 'transform 0.3s ease'
});

globalStyle(`${toggleSwitch}.toggled`, {
  backgroundColor: '#4caf50'
});

globalStyle(`${toggleSwitch}.toggled .${slider}`, {
  transform: 'translateX(30px)'
});

export const mainDiv = style ({
  display:'flex'
})

export const toggleDiv = style ({
  display:'flex',
  alignItems:'center'
})

export const input = style ({
  width:'16%',
  padding:'1%',
  marginRight:'3%',
  backgroundColor:'#F4F4F4',
  boxShadow:vars.boxShadow.customInner,
  border:'none',
  borderRadius:'10px',
})

export const button = style({
  margin:'3%',
  marginLeft:'74%',
  width:"7%",
  height:'54px',
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