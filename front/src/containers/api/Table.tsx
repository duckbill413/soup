import '@/containers/api/table.css'
import { APITableData } from '@/types/apitable'
import Link from 'next/link'

export default function Table({ data }: APITableData) {
  return (
    <table>
      <thead>
        <tr>
          <th>도메인</th>
          <th>API 이름</th>
          <th>종류</th>
          <th>URI</th>
          <th colSpan={2}>설명</th>
        </tr>
      </thead>
      <tbody>
        {data.map((item) => (
          <tr key={item.id}>
            <td>{item.domain}</td>
            <td>
              <Link href={`api/${1}`}>{item.name}</Link>
            </td>
            <td>{item.method}</td>
            <td>{item.uri}</td>
            <td colSpan={2}>{item.desc}</td>
          </tr>
        ))}
        <tr>
          <td colSpan={6}>+ 새로 만들기</td>
        </tr>
      </tbody>
    </table>
  )
}
