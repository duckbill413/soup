import { style } from '@vanilla-extract/css'
import vars from '@/styles/variables.css'

export const point = style({
    backgroundColor:vars.color.lightGray,
    borderRadius:'50%',
    '::-webkit-outer-spin-button': {
        WebkitAppearance: 'none',
        margin: 0,
    },
    '::-webkit-inner-spin-button': {
        WebkitAppearance: 'none',
        margin: 0,
    },
    'selectors': {
        '&[type="number"]': {
            MozAppearance: 'textfield',
        },
    },
})

