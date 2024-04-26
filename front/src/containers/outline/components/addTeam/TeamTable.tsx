import * as styles from "@/containers/outline/styles/table/outlineTable.css"

interface TableRow {
  name: string;
  role: string;
  email: string;
}

function TeamTable ({ rows }: { rows: TableRow[] }) {
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
      {rows.map((row) => (
        <tr key={row.email}>
          <td>{row.name}</td>
          <td>{row.role}</td>
          <td><a href={row.email}>{row.email}</a></td>
        </tr>
      ))}
      </tbody>
    </table>
  )
}

export default TeamTable