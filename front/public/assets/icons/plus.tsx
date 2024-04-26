import React from 'react'

type Fill = {
  color: string
}

export default function PlusIcon({ color }: Fill) {
  return (
    <svg
      width="16"
      height="16"
      viewBox="0 0 16 16"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
    >
      <path
        d="M8 1.14286C11.7714 1.14286 14.8571 4.22857 14.8571 8C14.8571 11.7714 11.7714 14.8571 8 14.8571C4.22857 14.8571 1.14286 11.7714 1.14286 8C1.14286 4.22857 4.22857 1.14286 8 1.14286ZM8 0C3.6 0 0 3.6 0 8C0 12.4 3.6 16 8 16C12.4 16 16 12.4 16 8C16 3.6 12.4 0 8 0Z"
        fill={color}
      />
      <path
        d="M12.5714 7.42857H8.57143V3.42857H7.42857V7.42857H3.42857V8.57143H7.42857V12.5714H8.57143V8.57143H12.5714V7.42857Z"
        fill={color}
      />
    </svg>
  )
}
