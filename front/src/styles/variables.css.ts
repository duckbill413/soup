import { createGlobalTheme } from '@vanilla-extract/css'


const vars = createGlobalTheme(':root', {
  fontSize:{
    t1: '3rem',
    t2: '2.5rem',
    t3: '2rem',
    t4: '1.5rem',
    large: '1.5rem',
    medium: '1.1rem',
    small: '1rem',
    caption: '0.9rem',
    tiny: '0.7rem',
  },
  space: {
    tiny: '0.5rem',
    small: '1rem',
    base: '1.5rem',
    large: '3rem',
    xLarge: '4rem',
    xxLarge: '5rem',
  },
  color: {
    white: '#ffffff',
    main: '#349B00',
    lightGreen: '#4FEC00',
    cream: '#FFF1C0',
    brown: '#564300',
    orange: '#FF7E20',
    black: '#363636',
    gray: '#D9D9D9',
    lightGray: '#F4F4F4',
    deepGray: '#797979',
    blue: '#1068bf',
    skyBlue: '#cbe2f9',
    lightOrange: '#f5d9a8',


  },
  backgroundColor:{
    layout: '',
    black: '#363636',
  },
  boxShadow: {
    customInner :'inset 0 5px 5px 2px rgba(0, 0, 0, 0.2)',
    customOuter : '3px 4px 8px 4px rgba(0, 0, 0, 0.25)'
  },



})

export default vars;