import { globalStyle, style, styleVariants } from '@vanilla-extract/css'
import vars from '@/styles/variables.css'



export const chatHeader = style({
    display:'flex',
    justifyContent:'center',
})
export const hrSect = style({
    display: 'flex',
    flexBasis: '100%',
    alignItems: 'center',
    color: 'rgba(0, 0, 0, 0.35)',
    fontWeight: '700',
    fontSize: '15px',
    margin: '8px 0px',
    '::before':{
        content: "",
        flexGrow: 1,
        background: 'rgba(0, 0, 0, 0.35)',
        height: '1px',
        fontSize: '0px',
        lineHeight: '0px',
        margin: '0px 16px',
    },
    '::after':{
        content: "",
        flexGrow: 1,
        background: 'rgba(0, 0, 0, 0.35)',
        height: '1px',
        fontSize: '0px',
        lineHeight: '0px',
        margin: '0px 16px',
    }
})


export const chatModalContentList = styleVariants({
    layout: [{ display: 'flex', }],
    layoutMe: [{ display: 'flex', justifyContent:'end', }],
    profile: [{ marginRight: vars.space.small,width:'10%'}],
    userArea: [{ display: 'flex', flexDirection: 'column', marginRight: vars.space.tiny }],
    content: [{

        padding: vars.space.tiny,
        margin: `0px 0px 5px 0px`,
        borderRadius: '10px',
        backgroundColor: vars.color.white,
    }],
    nickname: [{ margin: `0px 0px 5px 0px` }],
    time: [{ display: 'flex', alignItems: 'flex-end', fontSize: vars.fontSize.caption, color: vars.color.deepGray,marginRight:'10px' }],
})
globalStyle(`${chatModalContentList.profile} > img`, {
    borderRadius: '50%',
})

globalStyle(`${chatModalContentList.userArea} > div`, {
    display: 'flex',
})
