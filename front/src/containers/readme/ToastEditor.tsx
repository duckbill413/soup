'use client'

import React, { useRef } from 'react'
import '@toast-ui/editor/dist/toastui-editor.css';
import { Editor } from '@toast-ui/react-editor';
import '@toast-ui/editor-plugin-code-syntax-highlight/dist/toastui-editor-plugin-code-syntax-highlight.css';
import codeSyntaxHighlight from '@toast-ui/editor-plugin-code-syntax-highlight';
import 'tui-color-picker/dist/tui-color-picker.css';
import '@toast-ui/editor-plugin-color-syntax/dist/toastui-editor-plugin-color-syntax.css';
import colorSyntax from '@toast-ui/editor-plugin-color-syntax';
import Prism from 'prismjs';

// Todo 지우기
const DummyData = `![image](https://uicdn.toast.com/toastui/img/tui-editor-bi.png)

# Awesome Editor!

It has been _released as opensource in 2018_ and has ~~continually~~ evolved to **receive 10k GitHub ⭐️ Stars**.

## Create Instance

You can create an instance with the following code and use \`getHtml()\` and \`getMarkdown()\` of the [Editor](https://github.com/nhn/tui.editor).

\`\`\`js
const editor = new Editor(options);
\`\`\`

> See the table below for default options
> > More API information can be found in the document

| name | type | description |
| --- | --- | --- |
| el | \`HTMLElement\` | container element |

## Features

* CommonMark + GFM Specifications
   * Live Preview
   * Scroll Sync
   * Auto Indent
   * Syntax Highlight
        1. Markdown
        2. Preview

## Support Wrappers

> * Wrappers
>    1. [x] React
>    2. [x] Vue
>    3. [ ] Ember`

export default function ToastEditor() {

  const editorRef = useRef(null as Editor | null);

  const handleEditorChange = () => {
    const instance = editorRef.current?.getInstance().getMarkdown();
    // Todo 지우기
    console.log(instance);
  };

  return (
    <Editor
      initialValue={DummyData}
      previewStyle="vertical"
      height="68vh"
      plugins={[colorSyntax, [codeSyntaxHighlight, { highlighter: Prism }]]}
      initialEditType="markdown"
      // codeBlock 속성 추가
      codeBlock={{
        languages: [
          {
            language: 'javascript',
            syntax: /^```javascript/,
          },
          {
            language: 'java',
            syntax: /^```java/,
          },

        ],
        autoLanguageDetection: true,
      }}
      onChange={handleEditorChange}
      ref={editorRef}
    />

);
}
