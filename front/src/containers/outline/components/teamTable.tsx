import * as styles from "@/containers/outline/styles/outlineTable.css"

interface TableRow {
  name: string;
  role: string;
  email: string;
}

function TeamTable ({ rows }: { rows: TableRow[] }) {
  return (
    <table style={{ width: '80%' }}>
      <thead>
      <tr>
        <th className={styles.tableToolTitle}>이름</th>
        <th className={styles.tableURLTitle}>역할</th>
        <th className={styles.tableURLTitle}>e-mail</th>
      </tr>
      </thead>
      <tbody>
      {rows.map((row) => (
        <tr>
          <td className={styles.tableToolContent}>{row.name}</td>
          <td className={styles.tableToolContent}>{row.role}</td>
          <td className={styles.tableToolContent}><a href={row.email}>{row.email}</a></td>
        </tr>
      ))}
      </tbody>
    </table>
  )
}

export default TeamTable