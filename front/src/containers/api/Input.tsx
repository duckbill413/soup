import * as styles from '@/containers/api/input.css'
import { APIInput } from '@/types/apiinput'

interface LabelProps {
  title: string
  isEssential: boolean
  children: React.ReactNode
}

function InputLabel({ title, isEssential, children }: LabelProps) {
  return (
    <div className={styles.container}>
      <div className={styles.name}>
        <span className={styles.title}>{title}</span>
        <span
          className={styles.essential}
          style={isEssential ? { display: 'inline' } : { display: 'none' }}
        >
          *
        </span>
      </div>
      {children}
    </div>
  )
}

export function InputText({ title, isEssential, placeholder }: APIInput) {
  return (
    <InputLabel title={title} isEssential={isEssential}>
      <input
        id="text"
        type="text"
        placeholder={placeholder}
        className={styles.input}
        autoComplete="off"
      />
    </InputLabel>
  )
}

export function Dropbox({ title, isEssential, options }: APIInput) {
  return (
    <InputLabel title={title} isEssential={isEssential}>
      <div>
        <button type="button">
          {options ? options[0].value : '도메인이 없습니다.'}
        </button>
        <ul>{options?.map((item) => <li key={item.id}>{item.value}</li>)}</ul>
      </div>
    </InputLabel>
  )
}
