import '@/containers/api/table.css'

export default function Table() {
  return (
    <table>
      <thead>
        <tr>
          <th>도메인</th>
          <th>API 이름</th>
          <th>종류</th>
          <th>URI</th>
          <th>설명</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td>Board</td>
          <td>게시글 리스트 조회</td>
          <td>GET</td>
          <td>/board</td>
          <td>게시글을 전부 조회한다.</td>
        </tr>
        <tr>
          <td colSpan={5}>+ 새로 만들기</td>
        </tr>
      </tbody>
    </table>
  )
}
