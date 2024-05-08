import { globalStyle, style } from '@vanilla-extract/css'
import vars from '@/styles/variables.css'

/*
 Navigation(Left)의 CSS
 */
export const container = style({
  height: '100%',
  width: vars.space.large,
  position: 'fixed',
  display: 'flex',
  flexDirection: 'column',
  backgroundColor: vars.backgroundColor.black,
  padding: `${vars.space.xxLarge} ${vars.space.small} ${vars.space.tiny} ${vars.space.small}`,
  color: 'white',
  cursor: 'pointer',
  transitionDuration: '400ms',
  zIndex: 99,
  selectors: {
    '&:hover': {
      width: '150px',
    },
  },
})


// Icon에 관한
export const icon = style({
  width: '88%', paddingLeft: '3px', borderRadius: '10px', whiteSpace: 'nowrap', overflow: 'hidden', selectors: {
    '&:hover': {
      backgroundColor: vars.color.gray, color: vars.color.black,
    },
  },
})


// Hover시 나타나는 Paragraph
export const navName = style({
  position: 'relative', bottom: '15px', marginLeft: '30px', fontWeight: 'bold',
})

// Icon아래 있는 회색 Bar
export const bar = style({
  position: 'relative', right: '5px', width: '90%', padding: '1.2px', backgroundColor: vars.color.deepGray,
})

// Navigation 자식들 마다 마진바텀을 부여해줌
globalStyle(`${container} > *`, {
  marginBottom: vars.space.tiny,
})

// Icon아래 요소 1. IconImage, 2. Paragraph(해당이름)
globalStyle(`${icon} > *`, {
  width: '30px', display: 'inline',
})