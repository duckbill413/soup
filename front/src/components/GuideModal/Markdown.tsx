import 'highlight.js/styles/a11y-dark.css'
import Image from 'next/image'
import ReactMarkdown from 'react-markdown'
import { Prism as SyntaxHighlighter } from 'react-syntax-highlighter'
import rehypeRaw from 'rehype-raw'
import remarkGfm from 'remark-gfm'

type Props = {
  text: string
}

export default function Markdown({ text }: Props) {
  return (
    <ReactMarkdown
      remarkPlugins={[remarkGfm]}
      rehypePlugins={[rehypeRaw]}
      components={{
        ul({ children }) {
          return <ul style={{ paddingInlineStart: '16px' }}>{children}</ul>
        },
        li({ children }) {
          return <li style={{ paddingBlock: '4px' }}>{children}</li>
        },
        p({ children }) {
          return <p style={{ margin: 0 }}>{children}</p>
        },
        // 코드를 어떻게 표현할지에 대한 내용
        code({ className, children }) {
          // markdown에 사용된 언어
          const match = /language-(\w+)/.exec(className || '')
          // 사용된 언어가 표시되어있는 경우
          return match ? (
            <SyntaxHighlighter
              children={String(children).replace(/\n$/, '')}
              language={match[1]}
              PreTag="div"
            />
          ) : (
            // 사용된 언어를 따로 적지 않거나 적합하지 않을 경우
            <SyntaxHighlighter className={className}>
              {children as string}
            </SyntaxHighlighter>
          )
        },
        // nextjs의 경우 img를 Image 컴포넌트로 바꿔줌
        img: (image) => (
          <Image
            src={image.src || ''}
            alt={image.alt || ''}
            layout="responsive"
            width={820}
            height={150}
          />
        ),
      }}
    >
      {text}
    </ReactMarkdown>
  )
}
