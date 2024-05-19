import vars from '@/styles/variables.css'
import { globalStyle, style } from '@vanilla-extract/css'

export const button = style({
  position: 'relative',
})

export const icon = style({
  height: '44px',
  display: 'flex',
  alignItems: 'center',
})

export const backdrop = style({
  position: 'fixed',
  zIndex: '1500',
  inset: 0,
})

export const container = style({
  position: 'absolute',
  zIndex: '1600',
  top: `calc(${vars.space.xLarge} - 10px)`,
  right: 20,

  width: '480px',
  minHeight: '360px',

  borderRadius: '15px',
  background: vars.color.lightGray,
  boxShadow: vars.boxShadow.customOuter,
})

export const top = style({
  width: '100%',
  color: vars.color.black,
  background: 'white',
  padding: '12px 24px',
  borderRadius: '15px 15px 0 0',
  boxSizing: 'border-box',

  display: 'flex',
  alignItems: 'center',
  justifyContent: 'space-between',
})

export const title = style({
  fontSize: vars.fontSize.medium,
  fontWeight: 700,
})

export const sub = style({
  fontSize: vars.fontSize.caption,
  fontWeight: 500,
})

export const green = style({
  color: vars.color.main,
})

export const profile = style({
  borderRadius: '50%',
  aspectRatio: '1/1',
})

export const notiList = style({
  padding: '16px',
  margin: '8px 0',
})

export const notiTitle = style({
  fontSize: vars.fontSize.small,
  fontWeight: 600,
  alignmentBaseline: 'baseline',
})

export const date = style({
  fontSize: vars.fontSize.caption,
  color: vars.color.deepGray,
  minWidth: 'fit-content',
})

export const name = style({
  fontSize: vars.fontSize.caption,
  color: 'white',
  display: 'inline-block',
  padding: '0 8px',
  margin: '6px 0 0',
  borderRadius: '5px',
  background: vars.color.deepGray,
  width: 'fit-content',
})

export const notiTop = style({
  display: 'flex',
  justifyContent: 'space-between',
  alignItems: 'baseline',
  gap: '4px',
})

export const list = style({
  maxHeight: '440px',
  overflowY: 'auto',

  borderRadius: '0 0 15px 15px',

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
  },
})

export const notification = style({
  display: 'flex',
  gap: '10px',

  boxSizing: 'border-box',
})

export const contents = style({
  width: 'calc(100% - 45px)',
  display: 'flex',
  flexDirection: 'column',
  // gap: 'px',
})

globalStyle(`${notification} p`, {
  margin: '4px 0 0 0',
  overflow: 'hidden',
  whiteSpace: 'nowrap',
  textOverflow: 'ellipsis',

  fontSize: vars.fontSize.caption,
})
