import vars from '@/styles/variables.css'
import { keyframes, style } from '@vanilla-extract/css'

// 애니메이션 정의
const bounce = keyframes({
  '0%, 100%': {
    transform: 'translateY(-25%)',
    animationTimingFunction: 'cubic-bezier(0.8, 0, 1, 1)',
  },
  '50%': {
    transform: 'translateY(0)',
    animationTimingFunction: 'cubic-bezier(0, 0, 0.2, 1)',
  },
})

// bounce 스타일 생성
export const bounceAnimation = style({
  animation: `${bounce} 1s infinite`,
  margin: vars.space.tiny,
  padding: 0,
  background: 'none',
  border: 'none',
  position: 'absolute',
  bottom: '0',
})

export const pageContainer = style({
  height: '90vh',
  width: '100%',
  overflowY: 'auto',
  '::-webkit-scrollbar' : {
    display: 'none'
  }
})

export const mainPageHeader = style({
  display: 'flex',
  justifyContent: 'space-between',
  alignItems: 'center',
  height: '10vh',
  width: '95%',
  marginLeft: '2%',
})

export const mainPageHeaderBtn = style({
  padding: 0,
  background: 'none',
  border: 'none',
})
