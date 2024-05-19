import { globalStyle, style } from '@vanilla-extract/css'
import vars from '@/styles/variables.css'


export const btnGroupContainer = style({
    width: '25%',
    maxWidth:'50%',
    position: 'absolute',
    backgroundColor:'white',
    borderRadius: '5px',
    boxShadow: 'rgba(50, 50, 93, 0.25) 0px 13px 27px -5px, rgba(0, 0, 0, 0.3) 0px 8px 16px -8px',
    marginTop:'-3%',
    marginLeft: '-10%',
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
export const option = style({
    display:'flex',
    backgroundColor:'transparent',
    fontWeight: 500,
    border: 'none',
});

export const currCategory = style({

    display:'flex',
    borderRadius:'5px',
    padding: '2px',

})
globalStyle(`${elementGroup} > div > p`,{
    margin:0,
    cursor:'pointer'

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
export const whitesmoke = style({
    backgroundColor: 'whitesmoke'
})

export const button = style({
    display:'flex',
    alignItems:'center',
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


globalStyle(`${select}>span`, {
    marginRight: vars.space.tiny,
    color: vars.color.deepGray,
    fontWeight: 700
})
globalStyle(`${currCategory} > div`,{
    display:'flex',
    alignItems:'center',

});
globalStyle(`${btnGroupContainer} img`,{
    marginRight:vars.space.tiny,
    borderRadius:'50px'
});
globalStyle(`${currCategory} >  p`,{
    display:'flex',
    alignItems:'center',

});
