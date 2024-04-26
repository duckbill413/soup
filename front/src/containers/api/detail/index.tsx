import Table from '@/components/Table/Table'
import { TableHead } from '@/types/table'

const pathTableHeaders: Array<TableHead> = [
  {
    title: 'name',
    isEssential: true,
  },
  {
    title: 'type',
    isEssential: true,
  },
  {
    title: 'description',
    isEssential: false,
    colSpan: 2,
  },
  {
    title: 'default',
    isEssential: false,
  },
]

const queryTableHeaders: Array<TableHead> = [
  {
    title: 'name',
    isEssential: true,
  },
  {
    title: 'type',
    isEssential: true,
  },
  {
    title: 'required',
    isEssential: true,
  },
  {
    title: 'description',
    isEssential: false,
    colSpan: 2,
  },
  {
    title: 'default',
    isEssential: false,
  },
]

export function PathTable() {
  return (
    <Table headers={pathTableHeaders} hasNewLine={false}>
      <tr>
        <td>sort</td>
        <td>sort</td>
        <td colSpan={2}>sort</td>
        <td>sort</td>
      </tr>
    </Table>
  )
}

export function QueryTable() {
  return (
    <Table headers={queryTableHeaders} hasNewLine={false}>
      <tr>
        <td>sort</td>
        <td>sort</td>
        <td>sort</td>
        <td colSpan={2}>sort</td>
        <td>sort</td>
      </tr>
    </Table>
  )
}
