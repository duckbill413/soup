import { globalStyle, style } from '@vanilla-extract/css'
import vars from '@/styles/variables.css'

export const container = style({
  margin: `0 ${vars.space.large} ${vars.space.base} ${vars.space.base}`,

})



export const projectImage = style({
  borderRadius: '3%',
  '@media': {
    'screen and (max-width: 768px)': {
      width:'100%',
      height: '40%'
    },
  },
  transition: 'all 0.3s ease-out',

});

globalStyle(`${container}:hover ${projectImage}`, {
  transform: 'translateY(-5px) scale(1.005) translateZ(0)',
  boxShadow: `0 24px 36px rgba(0, 0, 0, 0.11), 0 24px 46px rgba(0, 0, 0, 0.2)`,
});

export const projectName = style({
  fontWeight: 'bold',
  margin: `${vars.space.tiny} 0 ${vars.space.tiny} 0`,
  borderRadius: '10%',
})


globalStyle(`${container} > div > img`,{
  borderRadius: '50%',
  marginRight: vars.space.tiny,

})