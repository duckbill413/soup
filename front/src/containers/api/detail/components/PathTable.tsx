'use client'

import Table from '@/components/Table/Table'
import { TableHead } from '@/types/table'
import { TextareaAutosize } from '@mui/base/TextareaAutosize'
import { useMutation, useStorage } from '../../../../../liveblocks.config'
import { Props } from '../types/props'
import SelectType from './SelectType'

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
]

export default function PathTable({ idx }: Props) {
  const initial = useStorage((root) => root.apiList)

  const updateLiveblock = useMutation(({ storage }, index, key, newValue) => {
    const apiList = storage.get('apiList')
    apiList?.get(idx)?.get('path_variable')?.get(index)?.set(key, newValue)
  }, [])

  return (
    <Table headers={pathTableHeaders}>
      {initial
        ? initial[idx].path_variable?.map((item, index) => (
            <tr key={index}>
              <td>{item.name}</td>
              <td aria-label="type">
                <SelectType
                  idx={item.type}
                  clickMenu={(typeIndex: number) =>
                    updateLiveblock(index, 'type', typeIndex)
                  }
                />
              </td>
              <td colSpan={2} aria-label="description">
                <TextareaAutosize
                  placeholder="설명 입력"
                  value={initial[idx].path_variable?.[index].desc}
                  onChange={(e) =>
                    updateLiveblock(index, 'desc', e.target.value)
                  }
                />
              </td>
            </tr>
          ))
        : null}
    </Table>
  )
}
