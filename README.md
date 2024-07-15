# spring-gift-jpa

## 기능 구현
- [x] 페이지네이션 구현
    - [x] 홈 html 수정
    - [x] product Controller, Service, Repository에 페이지네이션 기능 구현


## API 사용 예시

### 회원 등록

새로운 회원을 등록하기 위해 다음 cURL 명령어를 사용할 수 있습니다:

```bash
curl --location 'http://localhost:8080/members/register' \
--header 'Content-Type: application/json' \
--data-raw '{
  "email": "test@email.com",
  "password": "testpassword"
}'