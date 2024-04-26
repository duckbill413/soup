export type TableHead = {
  title: string
  isEssential: boolean
  colSpan?: number
}

export interface TableProps {
  headers: Array<TableHead>
  hasNewLine: boolean
  children: React.ReactNode
}
