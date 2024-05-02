import { globalStyle, style } from '@vanilla-extract/css'
import vars from '@/styles/variables.css'

export const container = style({
  margin: `0 ${vars.space.small} ${vars.space.base} ${vars.space.base}`,

})
export const projectImage = style({
  borderRadius: '3%',
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