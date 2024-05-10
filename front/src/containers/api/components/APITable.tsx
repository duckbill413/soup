'use client'

import Table from '@/components/Table/Table'
import APIDetail from '@/containers/api/detail'
import '@/containers/api/styles/apiTable.css'
import * as styles from '@/containers/api/styles/apiTable.css'
import { TableHead } from '@/types/table'
import { LiveList, LiveObject } from '@liveblocks/client'
import { useState } from 'react'
import { useMutation, useStorage } from '../../../../liveblocks.config'

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

export default function APITable() {
  const [open, setOpen] = useState(false)
  const [idx, setIdx] = useState(0)

  const data = useStorage((root) => root.apiList)

  const addRow = useMutation(({ storage }) => {
    const uuid = crypto.randomUUID()
    storage.get('apiList')?.push(
      new LiveObject({
        id: uuid,
        domain: '',
        name: '',
        method_name: '',
        http_method: '',
        uri: '',
        desc: '',
        path_variable: new LiveList(),
        query_param: new LiveList(),
        request_body: new LiveObject({ data: '{}', isValid: true }),
        response_body: new LiveObject({ data: '{}', isValid: true }),
      }),
    )
  }, [])

  const handleClick = () => {
    addRow()
  }

  return (
    <div>
      <Table headers={tableHeaders}>
        {data &&
          data.map((item, key) => (
            <tr
              key={item.id}
              onClick={() => {
                setOpen(true)
                setIdx(key)
              }}
            >
              <td>
                <span className={styles.domain}>{item.domain}</span>
              </td>
              <td>{item.name}</td>
              <td>{item.http_method}</td>
              <td>{item.uri}</td>
              <td colSpan={2}>{item.desc}</td>
            </tr>
          ))}
        <tr>
          <td
            className="newLine"
            colSpan={tableHeaders.reduce(
              (result, item) => result + (item.colSpan ? item.colSpan - 1 : 0),
              tableHeaders.length,
            )}
            onClick={handleClick}
          >
            + 새로 만들기
          </td>
        </tr>
      </Table>
      <APIDetail open={open} setOpen={setOpen} idx={idx} />
    </div>
  )
}
