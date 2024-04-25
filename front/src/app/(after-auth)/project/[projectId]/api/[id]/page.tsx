import { Dropbox, InputText } from '@/containers/api/Input'
import { Option } from '@/types/apiinput'
import Link from 'next/link'

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

export default function APIDetail() {
  return (
    <div>
      <Link href="/">목록으로 돌아가기</Link>
      <Dropbox title="도메인" isEssential options={sampleData} />
      <InputText
        title="API 이름"
        placeholder="[Swagger API] summary로 반영됩니다."
        isEssential
      />
      <InputText
        title="method 이름"
        placeholder="영어만 가능 / controller 메소드 이름으로 사용됩니다."
        isEssential
      />
      <InputText
        title="HTTP method 종류"
        placeholder="[Swagger API] summary로 반영됩니다."
        isEssential
      />
      <InputText
        title="URI path"
        placeholder="(예) /users/{userId}"
        isEssential
      />
      <InputText
        title="method 설명"
        placeholder={`[Swagger API] description 으로 반영됩니다.\n
        2줄 이상이 되면 이렇게 세로로 길어져요.\n
        제목은 세로 상 중간에 위치하고요. `}
        isEssential={false}
      />
    </div>
  )
}
