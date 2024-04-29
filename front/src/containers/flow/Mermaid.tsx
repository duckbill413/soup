'use client'

import React, { useState, useRef, useEffect } from 'react';
import dynamic from 'next/dynamic';
import mermaid from 'mermaid';

mermaid.initialize({
  startOnLoad: true,
  theme: 'default',
  securityLevel: 'loose',
});
// 서버 사이드 렌더링을 방지하기 위해 dynamic import 사용
const MDEditor = dynamic(() => import('@uiw/react-md-editor'), { ssr: false });

const mdMermaid = `
\`\`\`mermaid
graph TD
A[Hard] -->|Text| B(Round)
B --> C{Decision}
C -->|One| D[Result 1]
C -->|Two| E[Result 2]
\`\`\`

\`\`\`mermaid
sequenceDiagram
승원->>지우: 안녕 지우, 어떻게 지내?
loop 
    지우->>지우: 혼자 싸우기
end
Note right of 지우: 합리적인 생각들!
지우-->>승원: 좋아!
\`\`\`
`;

interface CodeProps {
  children: React.ReactNode;
  className?: string;
}
const getCode = (arr: React.ReactNode = []): string =>
  React.Children.toArray(arr)
    .map((dt) => {
      if (typeof dt === 'string') {
        return dt;
      }
      if (React.isValidElement(dt) && dt.props && dt.props.children) {
        return getCode(dt.props.children);
      }
      return '';
    })
    .filter(Boolean)
    .join('');


function Code ({ children = [], className }:CodeProps)  {
  const demoid = useRef(`dome${parseInt(String(Math.random() * 1e15), 10).toString(36)}`);
  const code = getCode(children);
  const demo = useRef<HTMLDivElement>(null);
  useEffect(() => {
    if (demo.current) {
      try {
        const str = mermaid.render(demoid.current, code, () => null, demo.current);
        demo.current.innerHTML = str;
      } catch (error) {
        demo.current.innerHTML = error as string;
      }
    }
  }, [code]);

  if (
    typeof code === 'string' &&
    typeof className === 'string' &&
    /^language-mermaid/.test(className.toLocaleLowerCase())
  ) {
    return (
      <div ref={demo}>
        <div id={demoid.current} style={{ display: 'none' }} />
      </div>
    );
  }
  return <code className={String(className)}>{children}</code>;
};


export default function Mermaid(){
  const [value, setValue] = useState(mdMermaid);
  return (
    <MDEditor
      onChange={(newValue = '') => setValue(newValue || '')}
      textareaProps={{
        placeholder: 'Markdown 텍스트를 입력해주세요'
      }}
      height={700}
      value={value}
      previewOptions={{
        components: {
          code: Code
        }
      }}
    />
  );
};