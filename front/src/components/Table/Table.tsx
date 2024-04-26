import * as styles from '@/components/Table/table.css'
import { TableProps } from '@/types/table'

export default function Table({ headers, hasNewLine, children }: TableProps) {
  return (
    <table>
      <thead>
        <tr>
          {headers.map((item) => (
            <th colSpan={item.colSpan ?? 1}>
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
              colSpan={
                (headers.reduce(
                  (result, item) => result + (item.colSpan ?? 1),
                  headers.length,
                ),
                0)
              }
            >
              + 새로 만들기
            </td>
          </tr>
        ) : null}
      </tbody>
    </table>
  )
}
