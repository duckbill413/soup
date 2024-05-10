import * as styles from "@/containers/outline/styles/table/outlineTable.css"
import { useStorage } from '../../../../../liveblocks.config'

function TeamTable () {
  const teamStorage = useStorage((root)=>root.outline)

  return (
    <table>
      <thead>
      <tr>
        <th className={styles.tableToolTitle}>이름</th>
        <th className={styles.tableURLTitle}>역할</th>
        <th className={styles.tableURLTitle}>e-mail</th>
      </tr>
      </thead>
      <tbody>
      {teamStorage?.project_team.map((row) => (
        <tr key={row.id}>
          <td>{row.name}</td>
          <td>
          {row.roles.map((role, index) => (
            <span key={`${role.role_id}-${index}`} className={styles.tdDetail}>{role.role_name}</span>
          ))}
          </td>
          <td><a href={`mailto:${row.email}`}>{row.email}</a></td>
        </tr>
      ))}
      </tbody>
    </table>
  )
}

export default TeamTable