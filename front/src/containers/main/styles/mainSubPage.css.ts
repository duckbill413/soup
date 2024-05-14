import { style } from '@vanilla-extract/css';
import vars from '@/styles/variables.css';

export const container = style({
  height: '90vh',
  width: '100%',
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'space-around',
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
});

export const funcContainer = style({
  height: '90vh',
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'space-around',
  backgroundColor: '#f4cfdf',
});

export const funcSubDiv = style ({
  display: 'flex',
  flexDirection: 'column',
  alignItems: 'flex-end',
})