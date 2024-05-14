import { globalStyle, keyframes, style, styleVariants } from '@vanilla-extract/css'
import vars from '@/styles/variables.css'

// 크기를 조절해주는 함수 transfromOrigin은 오른쪽하단 상태를 고정시켜준다.
const scaleDownUp = keyframes({
  '0%': {
    transform: 'scale(1)',
    transformOrigin: '100% 100%',
  },
  '100%': {
    transform: 'scale(0) translateY(50%)',
    transformOrigin: '100% 100%',
  },
})

const scaleUpDown = keyframes({
  '0%': {
    transform: 'scale(0) translateY(50%)',
    transformOrigin: '100% 100%',
  },
  '100%': {
    transform: 'scale(1)',
    transformOrigin: '100% 100%',
  },
})

// SVG를 클릭했을 때 나오는 화면
export const chatModal = style({
  width: '400px',
  height: '550px',
  display: 'flex',
  flexDirection: 'column',
  position: 'fixed',
  right: '3%',
  bottom: '13%',
  backgroundColor: vars.color.white,
  boxShadow: 'rgba(0, 0, 0, 0.24) 0px 3px 8px',
  borderRadius: '3px',
  zIndex:100
})

export const chatModalAnimation = styleVariants({
  first: {}, // 첫렌더링 시 애니메이션 적용x
  before: [{ animation: `${scaleDownUp} 0.4s forwards` }],
  after: [{ animation: `${scaleUpDown} 0.4s forwards` }],
})

export const chatModalContent = styleVariants({
  header: [{ padding: vars.space.small, fontWeight: 'bold', fontSize: vars.fontSize.medium }],
  background: [{ width: '100%', height: '95%', backgroundColor: vars.color.lightGray ,overflowY: 'auto',
    display:'flex',
    flexDirection: 'column-reverse'
    ,
    '::-webkit-scrollbar': {
      width: vars.space.tiny,
    },

    '::-webkit-scrollbar-thumb': {
      height: '5%',
      background: '#D3D3D3',
      backgroundClip: 'padding-box',
      borderWidth: '5px 2px',
      borderStyle: 'solid',
      borderColor: 'transparent',
      borderRadius: '15px',
    },},

  ],
  send: [{
    height: '9%',
    display: 'flex',
    alignItems: 'center',
    padding: `${vars.space.tiny} ${vars.space.base} ${vars.space.tiny} ${vars.space.base}`,
  }],

})
globalStyle(`${chatModalContent.background} > div`, {
  padding: vars.space.small,
})
globalStyle(`${chatModalContent.send} > div`, {
  width: '100%',
  height: '90%',
  border: `1.5px solid ${vars.color.gray}`,
  borderRadius: '5px',
  display: 'flex',
  alignItems: 'center',
  fontWeight: 'bold',
  paddingRight: '10px',
})


export const input = style({
  width: '90%',
  borderWidth: 0,
  marginLeft: '5px',
  selectors: {
    '&:focus': {
      outline: 'none',
    },
    '&::placeholder': {
      color: vars.color.gray,
    },
  },
})