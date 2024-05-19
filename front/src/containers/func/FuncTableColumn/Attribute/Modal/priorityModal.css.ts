import { globalStyle, style } from '@vanilla-extract/css'
import vars from '@/styles/variables.css'


export const btnGroupContainer = style({
    width: '15%',
    maxWidth:'50%',
    position: 'absolute',
    backgroundColor:'white',
    borderRadius: '5px',
    boxShadow: 'rgba(50, 50, 93, 0.25) 0px 13px 27px -5px, rgba(0, 0, 0, 0.3) 0px 8px 16px -8px',
    marginTop:'-3%',
    marginLeft: '2%',
    textAlign:'start',
    float: 'left',
    zIndex:3,

})
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
export const bar = style({
    display:'flex',

    // backgroundColor:'blue'
})
export const select = style({
    display:'flex',
    alignItems:'center',
    borderRadius:'4px',
    cursor:'pointer',
    marginBottom: '5px',
    ':hover':{
        backgroundColor: 'whitesmoke'
    }
})
export const whitesmoke = style({
    backgroundColor: 'whitesmoke'
})

export const button = style({
    borderRadius:'4px',
    padding:'3px',

})

export const clickBackground=style({

    width:'96vw',
    height:'90vh',
    position:'absolute',
    top:0,
    right:0,
    bottom:0,
    left:0,
    zIndex:1,
})


globalStyle(`${select}> img`, {
    marginRight: vars.space.tiny,
    color: vars.color.deepGray,
    fontWeight: 700
})

