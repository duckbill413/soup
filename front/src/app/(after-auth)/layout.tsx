import { Metadata } from 'next'

export const metadata: Metadata = {
  title: { template: '%s | SOUP', default: '내 프로젝트' },
}

export default function ProjectLayout({
  children,
}: Readonly<{ children: React.ReactNode[] }>) {
  return { ...children }
}
