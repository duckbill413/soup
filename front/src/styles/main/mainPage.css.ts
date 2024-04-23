import { style, keyframes } from '@vanilla-extract/css';

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
});

// bounce 스타일 생성
export const bounceAnimation = style({
  animation: `${bounce} 1s infinite`,
});