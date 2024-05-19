export interface IconButtonProps {
  name: string
  children: React.ReactNode
  eventHandler: MouseEventHandler<HTMLButtonElement> | string
}
