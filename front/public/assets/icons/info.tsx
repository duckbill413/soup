import React from 'react'

type Fill = {
  color: string
}

const infoIcon = ({ color }: Fill) => (
  <svg
    width="18"
    height="18"
    viewBox="0 0 26 26"
    fill="none"
    xmlns="http://www.w3.org/2000/svg"
  >
    <path
      d="M13 0C5.824 0 0 5.824 0 13C0 20.176 5.824 26 13 26C20.176 26 26 20.176 26 13C26 5.824 20.176 0 13 0ZM14.3 19.5H11.7V11.7H14.3V19.5ZM14.3 9.1H11.7V6.5H14.3V9.1Z"
      fill={color}
    />
  </svg>
)

export default infoIcon
