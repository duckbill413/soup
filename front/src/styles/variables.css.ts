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
  },
  space: {
    tiny: '0.5rem',
    small: '1rem',
    base: '1.5rem',
    large: '3rem',
    xLarge: '4rem',
  },
  color: {
    white: '#ffffff',
    main: '#349B00',
    cream: '#FFF1C0',
    brown: '#564300',
    orange: '#FF7E20',
    black: '#363636',
    gray: '#D9D9D9',

  },
  backgroundColor:{
    layout: '',
    black: '#363636',
  },




})

export default vars;