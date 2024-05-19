import { globalStyle, style } from '@vanilla-extract/css'
import vars from '@/styles/variables.css'


export const container = style({
    cursor:'pointer'
})
export const category = style({
    width:'100%',
    minWidth:'40%',
    padding:'4px',
    borderRadius:'5px'
});

export const btnGroup = style({

})

globalStyle(`${btnGroup} div`,{
    padding: '2%'

});
export const elementGroup = style({
    display:'flex',
    minHeight:'30px',
    flexDirection:'column',
    borderRadius:'10px 10px 0 0',
    zIndex:2,
    backgroundColor: vars.color.lightGray,

})


globalStyle(`${elementGroup} > div > p`,{
    margin:0,
    cursor:'pointer'

});
globalStyle(`${btnGroup} > div > p`,{
    fontSize: vars.fontSize.tiny,
    fontWeight: '500',
    color: vars.color.deepGray

});

export const select = style({
    borderRadius:'4px',
    cursor:'pointer',
    padding: '3px',
    margin: '0 0 3px 0',
    ':hover':{
        backgroundColor: 'whitesmoke'
    }
})

export const button = style({
    borderRadius:'4px',
    padding:'3px',

})


export const createCategory = style({
    display:'flex',
    alignItems: 'center',
})


globalStyle(`${select}>span`, {
    marginRight: vars.space.tiny,
    color: vars.color.deepGray,
    fontWeight: 700
})


globalStyle(`${createCategory}>p`, {
    margin: `0 ${vars.space.tiny} 0 0`,
    fontWeight: 400
})
