import {globalStyle, style} from '@vanilla-extract/css'
import vars from '@/styles/variables.css'

export const container = style({
    width: '100%',
    height: '100%',
    overflow: 'auto',
    '::-webkit-scrollbar': {
        width: vars.space.tiny,
    },
    '::-webkit-scrollbar-thumb': {
        height: '5%',
        background: '#D3D3D3',
    },
})

export const content = style({
    padding: `calc( ${vars.space.xLarge} + ${vars.space.base} ) ${vars.space.base} 0 calc( ${vars.space.base} + ${vars.space.base} )`,

})

export const projects = style({
    display: 'flex',
    flexWrap: 'wrap',
})
export const loading = style({
    position:'absolute',
    width:'100%',
    height:'100%',
    backgroundColor:'white',
    top:0,
    right:0,
    left:0,
    bottom:0,
    zIndex:'10',
})
export const projectHeader = style({
    display: 'flex',
    justifyContent: 'space-between',
    alignItems:'center',
})
globalStyle(`${loading} > *`, {
    position: 'absolute',
    left:'40%',
})

globalStyle(`${content} >div>p`, {
    fontWeight: 'bold',
    fontSize: vars.fontSize.t4
})

globalStyle(`${content} >div>span>button`, {
    backgroundColor:vars.color.main,
    borderRadius:'10px',
    padding:vars.space.tiny,
    color:'white',
    fontWeight:'500',
    margin:'0',
    marginRight:'8px'
})

