# spring-gift-wishlist

---

## Step2

**요구 사항**<br>
사용자가 로그인하고 사용자별 기능을 사용할 수 있도록 구현한다.  
아래 예시와 같이 HTTP 메시지를 주고받도록 구현한다.

Request
```
POST /login/token HTTP/1.1
content-type: application/json
host: localhost:8080

{
"password": "password",
"email": "admin@email.com"
}
```
Response
```
HTTP/1.1 200
Content-Type: application/json

{
"accessToken": ""
}
```

**구현 기능**<br>
요구 사항에 맞추어 인증 기능을 구현한다
1. 회원가입 기능
2. 로그인 기능




