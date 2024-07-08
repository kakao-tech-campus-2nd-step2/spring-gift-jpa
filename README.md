# spring-gift-wishlist
## step 0
- [x] 1주차 과제 중 conflict resolve 하기
- [x] 1주차 과제를 merge
## step 1
### 기능 요구 사항 
- 상품을 추가하거나 수정하는 경우, 클라이언트로부터 잘못된 값이 전달될 수 있다. 잘못된 값이 전달되면 클라이언트가 어떤 부분이 왜 잘못되었는지 인지할 수 있도록 응답을 제공한다.
- 상품 이름은 공백을 포함하여 최대 15자까지 입력할 수 있다.
- 특수 문자 가능: ( ), [ ], +, -, &, /, _ (그 외 특수 문자 사용 불가)
- "카카오"가 포함된 문구는 담당 MD와 협의한 경우에만 사용할 수 있다.
### 구현할 기능 목록
- [x] Product 객체의 15자 제한 + 특수 문자 제한 유효성 검사 구현하기
- [x] "카카오"가 포함된 문구의 유효성 검사 구현하기
- [x] 유효성 검사에서 발생한 Exception을 처리하는 ExceptionHandler 구현하기
- [x] 만들어둔 서버 사이드 렌더링 페이지에 잘못된 값이 왜 잘못되었는지 인지할 수 있는 thymeleaf 템플릿 만들기
## step 2
### 기능 요구 사항
- 사용자가 로그인하고 사용자별 기능을 사용할 수 있도록 구현한다.
- 아래 예시와 같이 HTTP 메시지를 주고받도록 구현한다.
#### REQUEST
~~~
POST /login/token HTTP/1.1
content-type: application/json
host: localhost:8080

{
    "password": "password",
    "email": "admin@email.com"
}
~~~
#### RESPONSE
~~~
HTTP/1.1 200 
Content-Type: application/json

{
    "accessToken": ""
}
~~~
### 구현할 기능 목록
- [x] 사용자의 비밀번호와 이메일을 저장하는 Member, MemberDTO 클래스 생성
- [x] MemberDTO와 DB의 데이터 전달을 담당하는 Repository 클래스 생성
- [X] JWT 토큰 관련 기능 구현
- [X] 사용자가 로그인하면 요청를 매핑하여 응답하는 AuthController 구현
- [ ] thymeleaf 사용하여 로그인 페이지 구현 (선택)
## step 3
### 기능 요구 사항
- 이전 단계에서 로그인 후 받은 토큰을 사용하여 사용자별 위시 리스트 기능을 구현한다.
- 위시 리스트에 등록된 상품 목록을 조회할 수 있다.
- 위시 리스트에 상품을 추가할 수 있다.
- 위시 리스트에 담긴 상품을 삭제할 수 있다.
### 구현할 기능 목록
- [ ] WishListDTO 구현
  - 위시 리스트에 등록하거나 추가, 삭제할 상품들은 ProductDTO가 된다. 즉, List<Product> products를 필드 변수로 가져야 한다.
  - 위시 리스트 구분을 위해 DTO에는 사용자의 email이 들어가 있어야 한다. 
- [ ] WishListRepository
  - DB에 사용자별 위시 리스트들을 저장
  - 사용자의 email을 파라미터로 DB에서 해당 사용자의 위시 리스트를 반환하는 메서드를 구현해야 한다.
- [ ] WishListService
  - WishListRepository를 생성자 주입으로 의존성 주입. 그리고 Repository의 메서드를 호출
  - 사용자의 위시리스트에 상품을 추가하거나 삭제하는 메서드를 구현해야 한다.
- [ ] WishListController
  - GET 요청에 대해 authorization header로 token을 읽어낸 다음, JwtUtil을 이용하여 확인 후 위시 리스트 표시
  - 상품을 추가하거나 삭제하는 POST, DELETE 요청도 동일한 방식으로 구현