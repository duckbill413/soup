'use client'

import { useEffect } from 'react'
import { ErdEditorElement } from '@dineug/erd-editor'

import * as styles from '@/containers/erd/erd.css'

export default function ERDDrawing() {
  // Define generateVuerd function before using it in useEffect
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

  useEffect(() => {
    const loadErdEditor = async () => {
      await import('@dineug/erd-editor')
      generateVuerd()
    }

    loadErdEditor()
  }, [])

  return <div className={styles.container} id="app-erd" />
}
