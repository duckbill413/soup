'use client'

import Table from '@/components/Table/Table'
import { TableHead } from '@/types/table'
import { LiveObject } from '@liveblocks/client'
import { TextareaAutosize } from '@mui/base/TextareaAutosize'
import Checkbox from '@mui/material/Checkbox'
import { useState } from 'react'
import { useMutation, useStorage } from '../../../../../liveblocks.config'
import ContextMenu from '../../components/ContextMenu'
import { Props } from '../types/props'
import SelectType from './SelectType'

const queryTableHeaders: Array<TableHead> = [
  {
    title: 'required',
    isEssential: true,
    colSpan: 1,
  },
  {
    title: 'name',
    isEssential: true,
    colSpan: 2,
  },
  {
    title: 'type',
    isEssential: true,
    colSpan: 2,
  },
  {
    title: 'description',
    isEssential: false,
    colSpan: 3,
  },
  {
    title: 'default',
    isEssential: false,
    colSpan: 2,
  },
]

export default function QueryTable({ idx }: Props) {
  const initial = useStorage((root) => root.apiList)
  const [anchorEl, setAnchorEl] = useState<null | {
    el: HTMLElement
    selectedIndex: number
  }>(null)

  const updateQueryParam = useMutation(({ storage }, index, key, newValue) => {
    const apiList = storage.get('apiList')
    apiList?.get(idx)?.get('query_param')?.get(index)?.set(key, newValue)
  }, [])

  const addRow = useMutation(({ storage }) => {
    storage
      .get('apiList')
      ?.get(idx)
      ?.get('query_param')
      ?.push(
        new LiveObject({
          name: '',
          type: 0,
          required: false,
          desc: '',
          default: '',
        }),
      )
  }, [])

  const deleteRow = useMutation(({ storage }, index) => {
    storage.get('apiList')?.get(idx)?.get('query_param')?.delete(index)
    setAnchorEl(null)
  }, [])

  const toggleRequired = useMutation(({ storage }, index) => {
    const apiList = storage
      .get('apiList')
      ?.get(idx)
      ?.get('query_param')
      ?.get(index)
    apiList?.set('required', !apiList.get('required'))
  }, [])

  return (
    <Table headers={queryTableHeaders}>
      {initial
        ? initial[idx].query_param?.map((item, index) => (
            <tr
              key={index}
              onContextMenu={(e) => e.preventDefault()}
              onMouseDown={(e) => {
                if (e.button === 2) {
                  setAnchorEl({ selectedIndex: index, el: e.currentTarget })
                }
              }}
            >
              <td colSpan={1} aria-label="required">
                <Checkbox
                  checked={initial[idx].query_param?.[index].required}
                  onChange={() => toggleRequired(index)}
                  inputProps={{ 'aria-label': 'controlled' }}
                />
              </td>
              <td colSpan={2} aria-label="name">
                <TextareaAutosize
                  placeholder="이름 입력"
                  value={initial[idx].query_param?.[index].name}
                  onChange={(e) =>
                    updateQueryParam(index, 'name', e.target.value)
                  }
                />
              </td>
              <td colSpan={2} aria-label="type">
                <SelectType
                  idx={item.type}
                  clickMenu={(typeIndex: number) =>
                    updateQueryParam(index, 'type', typeIndex)
                  }
                />
              </td>
              <td colSpan={3} aria-label="description">
                <TextareaAutosize
                  placeholder="설명 입력"
                  value={initial[idx].query_param?.[index].desc}
                  onChange={(e) =>
                    updateQueryParam(index, 'desc', e.target.value)
                  }
                />
              </td>
              <td colSpan={2} aria-label="default">
                <TextareaAutosize
                  placeholder="기본값 입력"
                  value={initial[idx].query_param?.[index].default}
                  onChange={(e) =>
                    updateQueryParam(index, 'default', e.target.value)
                  }
                />
              </td>
              <ContextMenu
                handleDelete={deleteRow}
                index={index}
                anchorEl={anchorEl}
                setAnchorEl={setAnchorEl}
              />
            </tr>
          ))
        : null}
      <tr>
        <td
          className="newLine"
          colSpan={queryTableHeaders.reduce(
            (result, item) => result + (item.colSpan ? item.colSpan - 1 : 0),
            queryTableHeaders.length,
          )}
          onClick={addRow}
        >
          + 새로 만들기
        </td>
      </tr>
    </Table>
  )
}
