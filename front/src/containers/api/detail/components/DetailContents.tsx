'use client'

import { getDomainNamesAPI } from '@/apis/api'
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
import { LiveList, LiveObject } from '@liveblocks/client'
import { useParams } from 'next/navigation'
import { ChangeEvent, useEffect, useState } from 'react'
import { useMutation, useStorage } from '../../../../../liveblocks.config'
import JsonEditor from './JsonEditor'

export default function DetailContents({ idx }: Props) {
  const params = useParams()
  const { projectId } = params
  const [domain, setDomain] = useState(null)
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

  // request, response body 변경 함수
  const handleChangeBody = (
    valid: boolean | undefined,
    key: string,
    newValue: string,
  ) => {
    const newBody = {
      data: newValue,
      isValid: valid ?? false,
    }
    try {
      JSON.parse(newValue)
      newBody.isValid = true
    } catch {
      if (newValue.length < 1) newBody.isValid = true
      else newBody.isValid = false
    }
    updateLiveblock(key, newBody)
  }

  const setDomainNames = async () => {
    if (projectId && typeof projectId === 'string') {
      const res = await getDomainNamesAPI(projectId)
      setDomain(res.result)
    }
  }

  useEffect(() => {
    setDomainNames()
  }, [])

  return (
    <section className={styles.inputSection}>
      <Dropbox
        title="도메인"
        isEssential
        options={domain ?? []}
        value={initial ? initial[idx]?.domain : ''}
        onChange={(e: ChangeEvent<HTMLInputElement>) =>
          updateLiveblock('domain', e.target.value)
        }
      />
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
        placeholder="(예) findSoup / 영어만 가능"
        isEssential
        value={initial ? initial[idx]?.method_name : ''}
        onChange={(e: ChangeEvent<HTMLInputElement>) => {
          const check = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/
          const { value } = e.target
          if (!check.test(value)) updateLiveblock('method_name', value)
        }}
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
        placeholder="(예) /soup/{soupId}"
        isEssential
        value={initial ? initial[idx]?.uri : ''}
        onChange={(e: ChangeEvent<HTMLInputElement>) =>
          handleChangeUri('uri', e.target.value)
        }
      />
      <InputText
        title="method 설명"
        placeholder="[Swagger API] description 으로 반영됩니다."
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

      {initial && initial[idx]?.http_method !== 'GET' ? (
        <div className={styles.requestBody}>
          <div className={styles.badgeAndError}>
            <Badge name="Request Body" />
            {initial && initial[idx]?.request_body?.isValid ? null : (
              <span className={styles.errorMsg}>
                * 올바른 Json 형식이 아닙니다.
              </span>
            )}
          </div>
          <JsonEditor
            body={initial ? initial[idx]?.request_body : undefined}
            handleChange={(e: string) =>
              handleChangeBody(
                initial[idx]?.request_body?.isValid,
                'request_body',
                e,
              )
            }
          />
        </div>
      ) : null}

      <div>
        <div className={styles.badgeAndError}>
          <Badge name="Response Body" />
          {initial && initial[idx]?.response_body?.isValid ? null : (
            <span className={styles.errorMsg}>
              * 올바른 Json 형식이 아닙니다.
            </span>
          )}
        </div>
        <JsonEditor
          body={initial ? initial[idx]?.response_body : undefined}
          handleChange={(e: string) =>
            handleChangeBody(
              initial && initial[idx]?.response_body?.isValid,
              'response_body',
              e,
            )
          }
        />
      </div>
    </section>
  )
}
