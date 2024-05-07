import { Dropbox, InputText, MethodButton } from '@/containers/api/Input'
import { PathTable, QueryTable } from '@/containers/api/detail'
import Badge from '@/containers/api/detail/Badge'
import * as styles from '@/containers/api/detail/index.css'
import { Option } from '@/types/apiinput'
import { KeyboardDoubleArrowLeft } from '@mui/icons-material'
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
      <Link href="." className={styles.backButton}>
        <KeyboardDoubleArrowLeft sx={{ paddingRight: '12px' }} />
        <span className={styles.buttonName}>목록으로 돌아가기</span>
      </Link>
      <section className={styles.inputSection}>
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
        <MethodButton title="HTTP method 종류" isEssential />
        <InputText
          title="URI path"
          placeholder="(예) /users/{userId}"
          isEssential
        />
        <InputText
          title="method 설명"
          placeholder={`[Swagger API] description 으로 반영됩니다.
2줄 이상이 되면 이렇게 세로로 길어져요.
제목은 세로 상 중간에 위치하고요. `}
          isEssential={false}
          multiline
        />

        <Badge name="Path Variable" />
        <PathTable />

        <Badge name="Query Parameter" />
        <QueryTable />

        <Badge name="Request Body" />
      </section>
    </div>
  )
}
