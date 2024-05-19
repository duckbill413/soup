import { Metadata } from 'next'

export const metadata: Metadata = {
  title: '기능 명세서',
}

export default function FuncLayout({
  children,
}: Readonly<{ children: React.ReactNode[] }>) {
  return { ...children }
}
