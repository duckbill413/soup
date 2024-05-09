'use client'

import {
  Dropbox,
  InputText,
  MethodButton,
} from '@/containers/api/components/Input'
import Badge from '@/containers/api/detail/components/Badge'
import PathTable from '@/containers/api/detail/components/PathTable'
import QueryTable from '@/containers/api/detail/components/QueryTable'
import * as styles from '@/containers/api/detail/styles/detailContents.css'
import { Props } from '@/containers/api/detail/types/props'
import { Option } from '@/containers/api/types/apiinput'
import { LiveList, LiveObject } from '@liveblocks/client'
import { ChangeEvent } from 'react'
import { useMutation, useStorage } from '../../../../../liveblocks.config'

// TODO: 예시 데이터. 삭제 필
const sampleData: Array<Option> = [
  {
    id: 1,
    value: 'Member',
  },
  {
    id: 2,
    value: 'Board',
  },
]

export default function DetailContents({ idx }: Props) {
  const initial = useStorage((root) => root.apiList)

  const updateLiveblock = useMutation(({ storage }, key, newValue) => {
    const apiList = storage.get('apiList')
    apiList?.get(idx)?.set(key, newValue)
  }, [])

  // uri 입력 시 path 추가 로직
  const handleChangeUri = (key: string, newValue: string) => {
    // uri 업데이트
    updateLiveblock(key, newValue)

    // 현재 path variable 가져오기
    const originPathNames = initial
      ? initial[idx].path_variable?.filter((item) => item.name)
      : []

    // 변경된 uri의 path 가져오기
    const pattern = '\\{(.*?)\\}'
    const newPathNames = Array.from(
      newValue.matchAll(new RegExp(pattern, 'g')),
      (match) => `${match[0].slice(1, -1)}`,
    )

    const newPathList = new LiveList()

    newPathNames.forEach((item) => {
      const originPath = originPathNames?.find((old) => old.name === item)
      if (originPath) {
        newPathList.push(
          new LiveObject({
            name: originPath.name,
            type: originPath.type,
            desc: originPath.desc,
          }),
        )
      } else {
        newPathList.push(
          new LiveObject({
            name: item,
            type: 0,
            desc: '',
          }),
        )
      }
    })
    updateLiveblock('path_variable', newPathList)
  }

  return (
    <section className={styles.inputSection}>
      <Dropbox title="도메인" isEssential options={sampleData} />
      <InputText
        title="API 이름"
        placeholder="[Swagger API] summary로 반영됩니다."
        isEssential
        value={initial ? initial[idx]?.name : ''}
        onChange={(e: ChangeEvent<HTMLInputElement>) =>
          updateLiveblock('name', e.target.value)
        }
      />
      <InputText
        title="method 이름"
        placeholder="영어만 가능 / controller 메소드 이름으로 사용됩니다."
        isEssential
        value={initial ? initial[idx]?.method_name : ''}
        onChange={(e: ChangeEvent<HTMLInputElement>) =>
          updateLiveblock('method_name', e.target.value)
        }
      />
      <MethodButton
        title="HTTP method 종류"
        isEssential
        value={initial ? initial[idx]?.http_method : null}
        onChange={(e: ChangeEvent<HTMLInputElement>) =>
          updateLiveblock('http_method', e.target.value)
        }
      />
      <InputText
        title="URI path"
        placeholder="(예) /users/{userId}"
        isEssential
        value={initial ? initial[idx]?.uri : ''}
        onChange={(e: ChangeEvent<HTMLInputElement>) =>
          handleChangeUri('uri', e.target.value)
        }
      />
      <InputText
        title="method 설명"
        placeholder={`[Swagger API] description 으로 반영됩니다.
2줄 이상이 되면 이렇게 세로로 길어져요.
제목은 세로 상 중간에 위치하고요. `}
        isEssential={false}
        multiline
        value={initial ? initial[idx]?.desc : ''}
        onChange={(e: ChangeEvent<HTMLInputElement>) =>
          updateLiveblock('desc', e.target.value)
        }
      />
      {initial &&
      initial[idx]?.path_variable &&
      initial[idx]?.path_variable?.length ? (
        <div>
          <Badge name="Path Variable" />
          <PathTable idx={idx} />
        </div>
      ) : null}

      <div>
        <Badge name="Query Parameter" />
        <QueryTable idx={idx} />
      </div>
    </section>
  )
}
