export const ReadMeTitle = [
  'Step1. ReadMe.md 구성하기',
  'Step2. HTML 및 MarkDown으로 작성하기'
]

export const ReadMeText = [
  `
  - ReadMe는 대부분 다음과 같은 구성입니다.
    - 프로젝트 구성
    - 프로젝트 프로그램 설치방법
    - 프로젝트 프로그램 사용법
    - 저작권 및 사용권 정보
    - 프로그래머 정보
    - 버그 및 디버그
    - 참고 및 출처
    - 버전 및 업데이트 정보
  `,
  `
  - 제목 (Headings)
    - HTML의 &lt;h1&gt;과 &lt;h1&gt;는 MarkDown에서 #과 ##으로 변환됩니다.
       \`\`\`\` 
       # First Sample

       ## Second Sample
       \`\`\`\` 
  - 목록 (Lists)
    - HTML의 순서 없는 목록 &lt;ul&gt;과 순서 있는 목록 &lt;ol&gt;은 \n
      MarkDown에서 각각 -와 숫자를 사용합니다.
    \`\`\`\` 
    - Unordered lists, and:
      1. One
      2. Two
      3. Three
    - More
    \`\`\`\`
  
  - 인용문 (Blockquote)
    - HTML의 &lt;blockquote&gt;는 Markdown에서 &gt;로 표시합니다.
    \`\`\`\` 
    > Blockquote
    \`\`\`\` 
    
  - 텍스트 강조 (Emphasis)
    - HTML의 &lt;strong&gt;, &lt;em&gt;, &lt;del&gt;는 Markdown에서 **, *, ~~로 변환됩니다.
     \`\`\`\` 
     And **bold**, *italics*, 
     and even *italics and later **bold***. 
     Even ~~strikethrough~~.
     \`\`\`\` 
     
  - 링크 (Links)
    - HTML의 &lt;a&gt; 태그는 Markdown에서 [텍스트]\\(URL\\) 형식으로 작성합니다.
      \`\`\`\` 
      [A link](https://markdowntohtml.com) to somewhere.
      \`\`\`\` 
 
  - 코드 (Code)
    - 인라인 코드: HTML의 &lt;code&gt;는 Markdown에서 백틱(\`)으로 감싸줍니다.
    - 코드 블록: 여러 줄의 코드는 세 개의 백틱(\`\`\`)과 언어 이름으로 감싸서 표시합니다.
  `
]