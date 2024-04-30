import { RadioButton, RadioButtonGroup } from '@/containers/build/RadioButton'
import * as styles from '@/containers/build/index.css'
import { TextField } from '@mui/material'

export function RadioSection() {
  return (
    <section className={styles.section}>
      <div className={styles.project}>
        <h4>Project</h4>
        <RadioButton checked label="Gradle - Groovy" />
      </div>
      <div className={styles.lang}>
        <h4>Language</h4>
        <RadioButton checked label="Java" />
      </div>
      <div className={styles.version}>
        <h4>Spring Boot</h4>
        <RadioButtonGroup />
      </div>
    </section>
  )
}

export function Metadata() {
  return (
    <div className={styles.metadata}>
      <h4>Project Metadata</h4>
      <TextField label="Group" variant="filled" size="small" />
      <TextField label="Artifact" variant="filled" size="small" />
      <TextField label="Name" variant="filled" size="small" />
      <TextField label="Description" variant="filled" size="small" />
      <TextField label="Package name" variant="filled" size="small" />
      <div className={styles.rowRadio}>
        <span>Packaging</span>
        <RadioButton checked label="Jar" />
      </div>
      <div className={styles.rowRadio}>
        <span>Java</span>
        <RadioButton checked label="17" />
      </div>
    </div>
  )
}
