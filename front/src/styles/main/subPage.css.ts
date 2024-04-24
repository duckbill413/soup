import { style } from '@vanilla-extract/css';
import vars from '@/styles/variables.css';

export const container = style({
  height: '90vh',
  width: '100%',
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'flex-end',
  backgroundColor: '#f7f6cf',
});

export const boldText = style({
  fontWeight: 'bold',
  fontSize: vars.fontSize.large,
  margin: vars.space.tiny,
});

export const text = style({
  fontSize: vars.fontSize.small,
  margin: vars.space.tiny,
});

export const planDiv = style({
  marginLeft: '17%',
});

export const funcContainer = style({
  height: '90vh',
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'flex-start',
  backgroundColor: '#f4cfdf',
});
