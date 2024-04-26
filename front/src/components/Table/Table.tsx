import * as styles from '@/components/Table/table.css'
import { TableProps } from '@/types/table'

export default function Table({ headers, hasNewLine, children }: TableProps) {
  return (
    <table className={styles.table}>
      <thead>
        <tr>
          {headers.map((item, k) => (
            <th colSpan={item.colSpan ?? 1} key={k}>
              {item.title}
              <span
                className={styles.essential}
                style={
                  item.isEssential ? { display: 'inline' } : { display: 'none' }
                }
              >
                *
              </span>
            </th>
          ))}
        </tr>
      </thead>
      <tbody>
        {children}
        {hasNewLine ? (
          <tr>
            <td
              className={styles.newLine}
              colSpan={headers.reduce(
                (result, item) =>
                  result + (item.colSpan ? item.colSpan - 1 : 0),
                headers.length,
              )}
            >
              + 새로 만들기
            </td>
          </tr>
        ) : null}
      </tbody>
    </table>
  )
}
