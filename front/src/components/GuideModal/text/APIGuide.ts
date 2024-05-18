export const APITitle = [
  'Step1. ERD와 연동',
  'Step2. 새로운 API 추가하기',
  'Step3. API 내용 입력',
  'Step4. 테이블에서 입력 데이터 삭제',
  'Step5. 프로젝트 빌드 시 반영',
]
export const APIText = [
  `
- API 명세서는 ERD, 빌드 기능과 연동이 가능합니다.
- API 명세서 작성 전, ERD를 먼저 완성하고 오시면 좋습니다.
    - ERD에서 생성한 Entity 중 하나를 Domain으로 선택할 수 있습니다.
`,
  `
![API 추가.gif](https://file.notion.so/f/f/7fe93563-b680-4a21-8a23-42c5ad3b7a81/f64fe7ce-954c-4b7c-b5dc-e332b4f9467e/API_%EC%B6%94%EA%B0%80.gif?id=5bde6208-a609-4d71-9924-3b9eecff4e97&table=block&spaceId=7fe93563-b680-4a21-8a23-42c5ad3b7a81&expirationTimestamp=1716120000000&signature=SofU_6trRvkMxFiwwNpUmnyP-Cki-dpn9xqlqMfOqUg&downloadName=API+%EC%B6%94%EA%B0%80.gif)

- 테이블 마지막 Row의 "+ 새로 추가하기"를 클릭하면 새로운 API가 생성됩니다.
- 하나의 Row 클릭 시 API 상세 내역을 확인할 수 있습니다.
`,
  `
- **method 이름**
    - 반드시 영어로 작성해주세요.
    - Java의 naming convention에 맞춰 camelCase로 작성해주세요.
    - 빌드 시: 해당 Entity의 Controller Class의 method 이름으로 반영됩니다.
- **URI path**
    - Path Variable를 추가할 경우 중괄호({}) 내에 변수 명을 입력해주세요.
    - Path Variable를 추가한 경우 하단에 정보 기입란이 나타납니다.
- **Path Variable, Query Parameter**
    - 표에서 정보를 기입할 수 있습니다.
    - 적용 가능한 Type
        - default : Object
        - 추가 선택 가능: String, int, Long, yyyyMMDD-hhmmss(Date), yyyyMMDD(Date)
- **Request Body, Response Body**
    - [JSON 문법](https://www.w3schools.com/whatis/whatis_json.asp#:~:text=JSON%20stands%20for%20JavaScript%20Object,describing%22%20and%20easy%20to%20understand)에 맞춰 작성해주세요.
        - 리스트의 경우 대괄호([])로 묶어주세요.
    - JSON Object의 예시를 작성해주세요.
        - JSON Object는 name : value 의 pair로 구성됩니다.
        - JSON data name에 따라 DTO가 생성됩니다.
        - JSON data value는 type을 고려한 dummy 값을 적어주세요.
        - type은 빌드 시 이하의 default type이 반영됩니다.
            
            
            | 입력 | 예시 | 변환 type |
            | --- | --- | --- |
            | 문자열 | “name”: “최지우” | String |
            | 숫자 | “id”: 1 | int |
        - type 변경 희망 시 빌드 후 직접 변경할 수 있습니다.
`,
  `
- API 테이블, Query Parameter 테이블에서 입력 데이터 삭제
    
    ![row 삭제.gif](https://file.notion.so/f/f/7fe93563-b680-4a21-8a23-42c5ad3b7a81/26f51269-2bb0-40ed-a848-3eaf4b10c870/row_%EC%82%AD%EC%A0%9C.gif?id=3a76b156-0e42-43fa-98b1-5903123edf48&table=block&spaceId=7fe93563-b680-4a21-8a23-42c5ad3b7a81&expirationTimestamp=1716120000000&signature=AtD2u_R-ppCRIvIAxAVZeANjgxvN45ji0OFXJTZO4ow&downloadName=row+%EC%82%AD%EC%A0%9C.gif)
    
    - Row를 마우스 우클릭 시 삭제가 가능합니다.
- Path Variable 테이블에서 입력 데이터 삭제
    
    ![Path Variable 삭제.gif](https://file.notion.so/f/f/7fe93563-b680-4a21-8a23-42c5ad3b7a81/93074f1e-0ab7-432c-98da-f029613af747/Path_Variable_%EC%82%AD%EC%A0%9C.gif?id=f6fcc144-0abf-44f0-80ed-a923d5c40e0a&table=block&spaceId=7fe93563-b680-4a21-8a23-42c5ad3b7a81&expirationTimestamp=1716120000000&signature=ViMjae5UkHWG4IGKgwPgrhCXy_Arzn8IgeQgcAZ8Z14&downloadName=Path+Variable+%EC%82%AD%EC%A0%9C.gif)
    
    - URI path에서 해당 Path Variable을 제거합니다.
`,
  `
- 프로젝트 파일 빌드 시 입력하신 Request body, Response body가 DTO로 생성됩니다.
- 빌드 예시
    - API 명세서
        - 도메인: Friend
        - API 이름: 유저의 친구 목록 조회
        - method 이름: getFriendList
        
        \`\`\`json
        {
          "id": 1,
          "product_id": 2,
          "name": "최지우",
          "friend_list": [
            {
              "name": "이수현",
              "age": 101
            }
          ]
        }
        \`\`\`
        
    - 프로젝트 빌드
        - FriendController.java
        
        \`\`\`java
        public class FriendController {
            public void getFriendList() {
              // todo
            }
        }
        \`\`\`
`,
]
