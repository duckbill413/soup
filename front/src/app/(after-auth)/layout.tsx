import { Metadata } from 'next'

export const metadata: Metadata = {
  title: { template: '%s | Soup', default: '내 프로젝트 | Soup' },
}

export default function ProjectLayout({
  children,
}: Readonly<{ children: React.ReactNode[] }>) {
  return { ...children }
}
