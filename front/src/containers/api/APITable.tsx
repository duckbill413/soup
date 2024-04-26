import Table from '@/components/Table/Table'
import '@/containers/api/apiTable.css'
import { APITableData } from '@/types/apitable'
import { TableHead } from '@/types/table'
import Link from 'next/link'
import * as styles from '@/containers/api/apiTable.css'

const tableHeaders: Array<TableHead> = [
  {
    title: '도메인',
    isEssential: false,
  },
  {
    title: 'API 이름',
    isEssential: false,
  },
  {
    title: '종류',
    isEssential: false,
  },
  {
    title: 'URI',
    isEssential: false,
  },
  {
    title: '설명',
    isEssential: false,
    colSpan: 2,
  },
]

export default function APITable({ data }: APITableData) {
  return (
    <Table headers={tableHeaders} hasNewLine>
      {data.map((item) => (
        <tr key={item.id}>
          <td>
            <span className={styles.domain}>{item.domain}</span>
          </td>
          <td>
            <Link href={`api/${1}`} className={styles.link}>
              {item.name}
            </Link>
          </td>
          <td>{item.method}</td>
          <td>{item.uri}</td>
          <td colSpan={2}>{item.desc}</td>
        </tr>
      ))}
    </Table>
  )
}
