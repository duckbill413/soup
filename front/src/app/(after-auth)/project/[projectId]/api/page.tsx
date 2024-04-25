import { StepTitleWithGuide } from '@/components/StepTitle/StepTitle'
import IconButton from '@/containers/api/IconButton'
import Table from '@/containers/api/Table'
import * as style from '@/containers/api/index.css'
import { APITableRow } from '@/types/apitable'
import { ErrorIcon, FilterIcon, SettingIcon } from '@/../public/assets/icons'

// TODO: 샘플데이터, api 연결 후 삭제 필

const sampleData: Array<APITableRow> = [
  {
    id: 1,
    domain: 'Board',
    domainColor: '#93B5E7',
    name: '게시글 리스트 조회',
    method: 'GET',
    uri: '/board',
    desc: '게시글을 전부 조회한다. 게시글을 하나만 조회할 수 있는겁니다. 뻥이고 사실 전부 조회할 수 있음요',
  },
  {
    id: 1,
    domain: 'Board',
    domainColor: '#93B5E7',
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
        desc="ERD를 기반으로 API 명세서를 작성해보세요."
        guideTitle="API 명세서 작성 가이드"
      />
      <div className={style.buttonGroup}>
        <IconButton name="정렬 기준" eventHandler="/">
          <FilterIcon color="currentColor" />
        </IconButton>
        <div className={style.right}>
          <IconButton name="DTO 관리" eventHandler="api/dto">
            <SettingIcon color="currentColor" />
          </IconButton>
          <IconButton name="에러 처리" eventHandler="api/error">
            <ErrorIcon color="currentColor" />
          </IconButton>
        </div>
      </div>
      <Table data={sampleData} />
    </div>
  )
}
