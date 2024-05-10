'use client'

import { langs } from '@uiw/codemirror-extensions-langs'
import { vscodeDark } from '@uiw/codemirror-theme-vscode'
import CodeMirror from '@uiw/react-codemirror'
import { Body } from '../../types/apilist'

interface Props {
  body: Body | undefined
  handleChange: Function
}

export default function JsonEditor({ body, handleChange }: Props) {
  return (
    <div>
      <CodeMirror
        editable
        value={body ? body.data : '{}'}
        height="50vh"
        basicSetup={{
          foldGutter: true,
          dropCursor: false,
          allowMultipleSelections: false,
          indentOnInput: false,
        }}
        theme={vscodeDark}
        extensions={[langs.json()]}
        style={{
          borderRadius: '10px',
          outline: 'none',
          overflow: 'auto',
          border: '1px solid #dedede',
        }}
        onChange={(e) => handleChange(e)}
      />
    </div>
  )
}
