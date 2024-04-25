import { StepTitleWithGuide } from '@/components/StepTitle/StepTitle'
import IconButton from '@/containers/api/IconButton'
import Table from '@/containers/api/Table'
import * as style from '@/containers/api/index.css'
import { ErrorIcon, FilterIcon, PlusIcon, SettingIcon } from '#/assets/icons'

//TODO: 샘플데이터, api 연결 후 삭제 필
const sampleData = [
  {
    domain: 'Board',
    name: '게시글 리스트 조회',
    method: 'GET',
    uri: '/board',
    desc: '게시글을 전부 조회한다.',
  },
  {
    domain: 'Board',
    name: '게시글 등록',
    method: 'POST',
    uri: '/board',
    desc: '게시글 하나 등록.',
  },
]

export default function ApiSpecification() {
  return (
    <div>
      <StepTitleWithGuide
        stepNum={6}
        title="API 명세서"
        desc="필요한 기능을 정리하고 JIRA와 연동해보세요."
        guideTitle="API 명세서 작성 가이드"
      />
      <div className={style.buttonGroup}>
        <IconButton name="정렬 기준" eventHandler="/">
          <FilterIcon color="currentColor" />
        </IconButton>
        <div className={style.right}>
          <IconButton name="API 추가" eventHandler="/add">
            <PlusIcon color="currentColor" />
          </IconButton>
          <IconButton name="DTO 관리" eventHandler="/dto">
            <SettingIcon color="currentColor" />
          </IconButton>
          <IconButton name="에러 처리" eventHandler="/error">
            <ErrorIcon color="currentColor" />
          </IconButton>
        </div>
      </div>
      <Table />
    </div>
  )
}
