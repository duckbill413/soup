'use client'

import { useEffect, useState } from 'react'
import { ErdEditorElement } from '@dineug/erd-editor'

import * as styles from '@/styles/project/projectDetail/erd/erd.css'

export function ERDDrawing() {
  // const [editor, setEditor] = useState()
  useEffect(() => {
    const loadErdEditor = async () => {
      const { default: ErdEditor } = await import('@dineug/erd-editor')
      generateVuerd()
    }

    loadErdEditor()
  }, [])

  const generateVuerd = () => {
    const container: any = document.querySelector('#app-erd')
    if (!container) return
    let editor: ErdEditorElement | null

    if (container.children.item(0)) {
      container.removeChild(container.children.item(0))
      editor = document.createElement('erd-editor')
    } else {
      editor = document.createElement('erd-editor')
    }
    if (!editor) return
    container.appendChild(editor)
    editor.systemDarkMode = false
  }

  return (
    <div className={styles.container} id="app-erd" />
  )
}