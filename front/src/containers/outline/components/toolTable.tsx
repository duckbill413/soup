import * as styles from "@/containers/outline/styles/outlineTable.css"

interface TableRow {
  name: string;
  description: string;
}

function ToolTable ({ rows }: { rows: TableRow[] }) {
  return (
    <table style={{ width: '80%' }}>
      <thead>
      <tr>
        <th className={styles.tableToolTitle}>툴 이름</th>
        <th className={styles.tableURLTitle}>URL 주소</th>
      </tr>
      </thead>
      <tbody>
      {rows.map((row) => (
        <tr>
          <td className={styles.tableToolContent}>{row.name}</td>
          <td className={styles.tableToolContent}><a href={row.description}>{row.description}</a></td>
        </tr>
      ))}
      </tbody>
    </table>
  )
}

export default ToolTable