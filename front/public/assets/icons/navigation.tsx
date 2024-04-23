import React from 'react'

type Fill = {
  color: string;
}

const ICON_SIZE = 48;

export const outline = ({ color }: Fill) => (
  <svg width={ICON_SIZE} height={ICON_SIZE} viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg">
    <path
      d="M30.55 24.95L23.05 17.4L28.6 11.9L27.15 10.45L17.6 20C17.2 20.4 16.7333 20.6 16.2 20.6C15.6667 20.6 15.2 20.4 14.8 20C14.4 19.6 14.2 19.125 14.2 18.575C14.2 18.025 14.4 17.55 14.8 17.15L24.3 7.65C25.1 6.85 26.0417 6.45 27.125 6.45C28.2083 6.45 29.15 6.85 29.95 7.65L31.4 9.1L33.9 6.6C34.3 6.2 34.775 6 35.325 6C35.875 6 36.35 6.2 36.75 6.6L41.4 11.25C41.8 11.65 42 12.125 42 12.675C42 13.225 41.8 13.7 41.4 14.1L30.55 24.95ZM8 42C7.43333 42 6.95833 41.8083 6.575 41.425C6.19167 41.0417 6 40.5667 6 40V36.15C6 35.6167 6.1 35.1083 6.3 34.625C6.5 34.1417 6.78333 33.7167 7.15 33.35L20.2 20.25L27.75 27.75L14.65 40.85C14.2833 41.2167 13.8583 41.5 13.375 41.7C12.8917 41.9 12.3833 42 11.85 42H8Z"
      fill={color} />
  </svg>)


export const plan = ({ color }: Fill) => (
  <svg width={ICON_SIZE} height={ICON_SIZE} viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg">
    <g clipPath="url(#clip0_340_576)">
      <path d="M28 39.7599V43.9999H32.24L42.58 33.6599L38.34 29.4199L28 39.7599Z" fill={color} />
      <path d="M40 16L28 4H12C9.8 4 8.02 5.8 8.02 8L8 40C8 42.2 9.78 44 11.98 44H24V38.1L40 22.1V16ZM26 18V7L37 18H26Z"
            fill={color} />
      <path
        d="M45.4198 28.0001L43.9998 26.5801C43.2198 25.8001 41.9598 25.8001 41.1798 26.5801L39.7598 28.0001L43.9998 32.2401L45.4198 30.8201C46.1998 30.0401 46.1998 28.7801 45.4198 28.0001Z"
        fill={color} />
    </g>
    <defs>
      <clipPath id="clip0_340_576">
        <rect width={ICON_SIZE} height={ICON_SIZE} fill={color} />
      </clipPath>
    </defs>
  </svg>)

export const func = ({ color }: Fill) => (
  <svg width={ICON_SIZE} height={ICON_SIZE} viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg">
    <g clipPath="url(#clip0_340_588)">
      <path
        d="M6 28H14V20H6V28ZM6 38H14V30H6V38ZM6 18H14V10H6V18ZM16 28H42V20H16V28ZM16 38H42V30H16V38ZM16 10V18H42V10H16Z"
        fill={color} />
    </g>
    <defs>
      <clipPath id="clip0_340_588">
        <rect width={ICON_SIZE} height={ICON_SIZE} fill={color} />
      </clipPath>
    </defs>
  </svg>

)

export const flow = ({ color }: Fill) => (
  <svg width={ICON_SIZE} height={ICON_SIZE} viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg">
    <g clipPath="url(#clip0_340_592)">
      <path d="M33.2 38.6L18.8 24.2H2V19.4H18.8L33.2 5L50 21.8L33.2 38.6Z" fill={color} />
    </g>
    <defs>
      <clipPath id="clip0_340_592">
        <rect width={ICON_SIZE} height={ICON_SIZE} fill={color} />
      </clipPath>
    </defs>
  </svg>

)

export const ERD = ({ color }: Fill) => (
  <svg width={ICON_SIZE} height={ICON_SIZE} viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg">
    <g clipPath="url(#clip0_340_595)">
      <path
        d="M38 14H18C15.8 14 14 15.8 14 18V38C14 40.2 15.8 42 18 42H38C40.2 42 42 40.2 42 38V18C42 15.8 40.2 14 38 14ZM38 18V22H18V18H38ZM26 30V26H30V30H26ZM30 34V38H26V34H30ZM22 30H18V26H22V30ZM34 26H38V30H34V26ZM18 34H22V38H18V34ZM34 38V34H38V38H34ZM12 34H10C7.8 34 6 32.2 6 30V10C6 7.8 7.8 6 10 6H30C32.2 6 34 7.8 34 10V12H30V10H10V30H12V34Z"
        fill={color} />
    </g>
    <defs>
      <clipPath id="clip0_340_595">
        <rect width={ICON_SIZE} height={ICON_SIZE} fill="white" />
      </clipPath>
    </defs>
  </svg>

)

export const API = ({ color }: Fill) => (
  <svg width={ICON_SIZE} height={ICON_SIZE} viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg">
    <path fillRule="evenodd" clipRule="evenodd"
          d="M24 48C37.2548 48 48 37.2548 48 24C48 10.7452 37.2548 0 24 0C10.7452 0 0 10.7452 0 24C0 37.2548 10.7452 48 24 48ZM36.0527 17V31.8H39.0127V17H36.0527ZM22.7129 31.8V17H27.5729C30.8329 17 33.3529 18.14 33.3529 21.66C33.3529 25.04 30.8329 26.54 27.6529 26.54H25.6729V31.8H22.7129ZM25.6729 24.2H27.4529C29.4929 24.2 30.4729 23.34 30.4729 21.66C30.4729 19.92 29.3929 19.36 27.3529 19.36H25.6729V24.2ZM12.74 17L8 31.8H11L12.0462 28H16.8138L17.86 31.8H20.96L16.22 17H12.74ZM16.186 25.72L15.74 24.1C15.4549 23.1023 15.1783 22.029 14.9045 20.9672C14.7558 20.3901 14.6079 19.8164 14.46 19.26H14.38C14.3493 19.3826 14.3186 19.5058 14.2877 19.6295C13.9155 21.1196 13.5263 22.678 13.12 24.1L12.674 25.72H16.186Z"
          fill={color} />
  </svg>

)

export const build = ({ color }: Fill) => (
  <svg width={ICON_SIZE} height={ICON_SIZE} viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg">
    <g clipPath="url(#clip0_340_607)">
      <path
        d="M45.4009 38L27.2009 19.8C29.0009 15.2 28.0009 9.80002 24.2009 6.00002C20.2009 2.00002 14.2009 1.20002 9.40085 3.40002L18.0009 12L12.0009 18L3.20086 9.40002C0.800855 14.2 1.80086 20.2 5.80086 24.2C9.60086 28 15.0009 29 19.6009 27.2L37.8009 45.4C38.6009 46.2 39.8009 46.2 40.6009 45.4L45.2009 40.8C46.2009 40 46.2009 38.6 45.4009 38Z"
        fill={color} />
    </g>
    <defs>
      <clipPath id="clip0_340_607">
        <rect width={ICON_SIZE} height={ICON_SIZE} fill={color} />
      </clipPath>
    </defs>
  </svg>


)

export const readme = ({ color }: Fill) => (
  <svg width={ICON_SIZE} height={ICON_SIZE} viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg">
    <g clipPath="url(#clip0_340_611)">
      <path
        d="M14 6H8V12H4V2H14V6ZM44 12V2H34V6H40V12H44ZM14 42H8V36H4V46H14V42ZM40 36V42H34V46H44V36H40ZM38 36C38 38.2 36.2 40 34 40H14C11.8 40 10 38.2 10 36V12C10 9.8 11.8 8 14 8H34C36.2 8 38 9.8 38 12V36ZM30 16H18V20H30V16ZM30 22H18V26H30V22ZM30 28H18V32H30V28Z"
        fill={color} />
    </g>
    <defs>
      <clipPath id="clip0_340_611">
        <rect width={ICON_SIZE} height={ICON_SIZE} fill={color} />
      </clipPath>
    </defs>
  </svg>

)

