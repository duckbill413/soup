import vars from '@/styles/variables.css'
import { globalStyle, style } from '@vanilla-extract/css'

export const container = style({
  height: vars.space.large,
  width: '99%',
  position: 'fixed',
  display: 'flex',
  justifyContent: 'space-between',
  backgroundColor: vars.backgroundColor.black,
  padding: vars.space.tiny,
  color: 'white',
  boxShadow: `0px 0px 5px black`,
  zIndex: 100,
})

export const whiteTheme = style({
  backgroundColor: 'white',
  boxShadow: '0px 0px',
})
export const assistant = style({
  display: 'flex',
  justifyContent: 'flex-end',
})

export const backdrop = style({
  position: 'fixed',
  zIndex: '1500',
  inset: 0,
})

export const menu = style({
  position: 'absolute',
  zIndex: '1600',
  top: `calc(${vars.space.xLarge} - 10px)`,
  right: 0,
  padding: '20px',
  gap: '4px',

  display: 'flex',
  flexDirection: 'column',
  justifyContent: 'center',

  borderRadius: '15px',
  background: 'white',
  boxShadow: vars.boxShadow.customOuter,
})

export const nickname = style({
  color: vars.color.black,
  fontSize: vars.fontSize.small,
  fontWeight: 700,
})

export const email = style({
  color: vars.color.deepGray,
  fontSize: vars.fontSize.caption,
  marginBottom: vars.space.base,
})

export const profile = style({
  borderRadius: '50%',
  aspectRatio: '1/1',
  alignSelf: 'center',
})

globalStyle(`${menu} > span`, {
  textAlign: 'center',
})

globalStyle(`${container} > div`, {
  width: '400px',
  height: '100%',
  display: 'flex',
  alignItems: 'center',
})
globalStyle(`${container} > div > p`, {
  marginLeft: vars.space.xLarge,
  fontSize: vars.fontSize.small,
  overflow: 'hidden',
  whiteSpace: 'nowrap',
})

globalStyle(`${assistant} > *`, {
  marginRight: vars.space.small,
  borderRadius: '50%',
  cursor: 'pointer',
})

globalStyle(`${assistant} > img`, {
  '@media': {
    'screen and (max-width: 768px)': {
      display: 'none',
    },
  },
})
