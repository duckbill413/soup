import { globalStyle, style } from '@vanilla-extract/css'
import vars from "@/styles/variables.css";

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
export const manager = style({
    width:'100%',
    height:'100%',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center'
})
globalStyle(`${manager}>p`, {

    margin: `0 0 0 ${vars.space.tiny}`
})

globalStyle(`${manager}>img`, {
    borderRadius: '50%',
    aspectRatio: 1
})
