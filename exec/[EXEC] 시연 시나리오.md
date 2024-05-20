# 시연 시나리오

## 0. 공통

|                        마우스 커서 공유                         |                        마우스 커서 대화                         |
|:--------------------------------------------------------:|:--------------------------------------------------------:|
| <img width="800" src="../assets/gifs/[s] 마우스 커서 공유.gif"> | <img width="800" src="../assets/gifs/[s] 마우스 커서 대화.gif"> |
|       모든 STEP 별 페이지마다, 팀원들의 마우스 커서 위치가 실시간으로 공유된다.       |          단축키 `ctrl` + `/` 를 통해 커서를 통해 대화할 수 있다.          |

| 동시 편집 |                        채팅                         |
|:------------------------------------------------------:|:-------------------------------------------------:|
|  <img width="800" src="../assets/gifs/[s] 동시 편집.gif">  | <img width="800" src="../assets/gifs/[s] 채팅.gif"> |
| modal을 제외한 모든 STEP 별 페이지마다, 팀원들이 작성/수정하는 내용은 실시간 공유된다. |             실시간으로 음성 및 텍스트 채팅이 가능하다.              |

| 태그 알림 (태그하는 사람) | 태그 알림 (태그당한 사람) |
|:-:|:-:|
| <img width="800" src="../assets/gifs/[s] 태그 알림 1 (mentioner).gif"> | <img width="800" src="../assets/gifs/[s] 태그 알림 2 (mentionee).gif"> |
|                 채팅 메시지를 통해 팀원을 태그하면 실시간 알림이 발생한다.                  | 알림 수신자는 알림을 클릭하면 해당 프로젝트의 개요 페이지로 이동한다. 해당 프로젝트 페이지 내에 위치할 경우, 채팅방 창이 켜지며 해당 채팅 메시지 위치로 이동한다. |

|                 STEP 페이지 별 가이드라인 제공                  |                                                                                |
|:----------------------------------------------------:|:---------------------------------------------------------------------------------------------:|
| <img width="800" src="../assets/gifs/[s] 가이드라인.gif"> |                             |
|       사용이 미숙한 사용자를 위해, 모든 STEP별 페이지마다 가이드라인 제공       |  |

## STEP 1. 프로젝트 개요

<img width="800" src="../assets/gifs/[s] 팀원 초대.gif">

- 간단한 프로젝트 정보를 입력한다.
- 팀원을 초대한다.
    - 기존 유저는 바로 초대되고, 미가입 유저에게는 초대 코드가 담긴 이메일이 발송된다.

## STEP 2. AI 기획서

<img width="800" src="../assets/gifs/[s] AI 기획서 생성.gif">

- 간단한 키워드나 문장을 작성하면 Claude를 활용한 기획서가 자동으로 작성된다.

## STEP 3. 기능 명세서

<img width="800" src="../assets/gifs/기능명세서.gif">

- 필요한 기능을 정의한다.
- 팀장 권한 유저에 한해, `JIRA 동기화` 버튼을 클릭하면, 프로젝트 개요 페이지에 등록한 Jira와 연동된다. 

## STEP 4. Flow Chart

- Mermaid를 활용해 Flow Chart를 작성한다. 

### 4-1. Sequence Diagram

```markdown
sequenceDiagram
    participant User as 사용자
    participant Client as 클라이언트(웹/앱)
    participant Server as 서버
    participant SocialLogin as 소셜 로그인 제공자

    User->>Client: 소셜 로그인 버튼 클릭
    Client->>SocialLogin: 로그인 요청 (OAuth 2.0)
    SocialLogin-->>User: 로그인 페이지 표시
    User->>SocialLogin: 로그인 정보 입력 및 동의
    SocialLogin-->>Client: 액세스 토큰 발급
    Client->>Server: 액세스 토큰 전송
    Server->>SocialLogin: 액세스 토큰 유효성 검사
    SocialLogin-->>Server: 유효한 액세스 토큰
    Server->>SocialLogin: 사용자 정보 요청
    SocialLogin-->>Server: 사용자 정보 응답
    alt 신규 사용자
        Server->>Server: 사용자 계정 생성
    else 기존 사용자
        Server->>Server: 사용자 계정 매핑
    end
    Server-->>Client: 로그인 성공 응답
    Client-->>User: 로그인 완료 및 메인 페이지 표시
```

- 예) 소셜 로그인 기능에 대한 Sequence Diagram
- 기능에 대해 보다 정확하게 정의할 수 있다.
- 포지션간, 팀원간 기능에 대해 정확한 이해를 공유하고 명확한 의사소통을 할 수 있다.

### 4-2. 개발 순서도

```markdown
graph TD
   A[프로젝트 시작] --> B{요구사항 분석}
   B --> C[시스템 설계]
   C --> D[아키텍처 설계]
   D --> E[데이터베이스 설계]
   E --> F[UI/UX 설계]
   F --> G[개발 환경 설정]
   G --> H[기능 구현]
   H --> I{단위 테스트}
   I -- 실패 --> H
   I -- 성공 --> J[통합 테스트]
   J -- 실패 --> H
   J -- 성공 --> K[시스템 테스트]
   K -- 실패 --> H
   K -- 성공 --> L[사용자 테스트]
   L -- 실패 --> H
   L -- 성공 --> M[버그 수정 및 성능 최적화]
   M --> N{최종 검토}
   N -- 미완료 --> M
   N -- 완료 --> O[프로젝트 문서화]
   O --> P[발표 자료 준비]
   P --> Q[프로젝트 발표]
   Q --> R[피드백 수렴]
   R --> S{프로젝트 종료}
   S -- 추가 개선 사항 --> H
   S -- 완료 --> T[프로젝트 종료]

```

## 5. ERD

<img width="800" src="../assets/gifs/ERD.gif">

- Vuerd를 기반으로 ERD를 작성한다.
- ERD 내용도 팀원간 실시간으로 공유된다.

## 6. API 명세서

|                    API 추가                    |                  API 내용 입력                   |
|:--------------------------------------------:|:--------------------------------------------:|
| <img width="800" src="assets/gifs/API1.gif"> | <img width="800" src="assets/gifs/API2.gif"> |

|                    API 등록                    |                API 데이터삭제                |
|:--------------------------------------------:| :------------------------------------------: |
| <img width="800" src="assets/gifs/API3.gif"> | <img width="800" src="assets/gifs/API4.gif"> |

- endpoint, 메소드 정보, Path Variable, Query Parameter, Request/Response Body 등 API를 정의한다.

## 7. README

- 앞서 작성한 내용을 기반으로 Markdown 문법의 Readme 내용이 작성된다.

## 8. Build

- 프로젝트의 구조, 정보를 작성한 후 프로젝트를 빌드한다.
- 빌드 완료 후 코드 구조를 확인하면 프로젝트 .zip 파일이 다운된다.
    - `ERD`에서 작성한 entity와 연관 관계 매핑이 반영된다.
    - `API 명세서`에서 작성한 method가 controller class에 반영된다.
    - readme.md가 포함된다.
- 프로젝트를 로컬에서 실행시킨 후, `/api/swagger-ui.html`에서 Swagger-ui를 확인 가능하다.
