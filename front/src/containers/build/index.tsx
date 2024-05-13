'use client'

import {
  RadioButton,
  RadioButtonGroup,
} from '@/containers/build/components/RadioButton'
import * as styles from '@/containers/build/index.css'
import { TextField } from '@mui/material'
import { useMutation, useStorage } from '../../../liveblocks.config'

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
  const data = useStorage((root) => root.build)

  const handleChange = useMutation(({ storage }, key, value) => {
    storage.get('build')?.get('metadata')?.set(key, value)
  }, [])

  const handleChangeGroup = useMutation(({ storage }, e) => {
    storage.get('build')?.get('metadata')?.set('group', e.target.value)
    const artifact = storage.get('build')?.get('metadata').get('artifact')
    handleChange('packageName', `${e.target.value}.${artifact}`)
  }, [])

  const handleChangeArtifact = useMutation(({ storage }, e) => {
    storage.get('build')?.get('metadata')?.set('artifact', e.target.value)
    const group = storage.get('build')?.get('metadata').get('group')
    handleChange('packageName', `${group}.${e.target.value}`)
    handleChange('name', `${e.target.value}`)
  }, [])

  return (
    <div className={styles.metadata}>
      <h4>Project Metadata</h4>
      <TextField
        label="Group"
        variant="filled"
        size="small"
        value={data?.metadata.group}
        onChange={handleChangeGroup}
      />
      <TextField
        label="Artifact"
        variant="filled"
        size="small"
        value={data?.metadata.artifact}
        onChange={handleChangeArtifact}
      />
      <TextField
        label="Name"
        variant="filled"
        size="small"
        value={data?.metadata.name}
        onChange={(e) => handleChange('name', e.target.value)}
      />
      <TextField
        label="Description"
        variant="filled"
        size="small"
        value={data?.metadata.description}
        onChange={(e) => handleChange('description', e.target.value)}
      />
      <TextField
        label="Package name"
        variant="filled"
        size="small"
        value={data?.metadata.packageName}
        onChange={(e) => handleChange('packageName', e.target.value)}
      />
      <div className={styles.rowRadio}>
        <span>Packaging</span>
        <RadioButton checked label={data?.packaging ?? 'Jar'} />
      </div>
      <div className={styles.rowRadio}>
        <span>Java</span>
        <RadioButton checked label={data?.javaVersion ?? '17'} />
      </div>
    </div>
  )
}
