import { globalStyle, style } from '@vanilla-extract/css'

export const category = style({
    width:'100%',
    minWidth:'40%',
    padding:'4px',
    borderRadius:'5px'
});

export const btnGroup = style({})

globalStyle(`${btnGroup} div`,{
    padding: '2%'

});
