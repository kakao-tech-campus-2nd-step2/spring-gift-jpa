# spring-gift-wishlist

## 1단계
### 클라이언트 입력 값에 대한 검증 및 예외 처리 구현
#### Model(Product)
- 스프링에서 제공하는 Validation 활용
- 공통 조건
  - null 입력 불가
  - 일치하지 않는 타입 입력 불가
- 상품명
    1. 공백을 포함하여 최대 15자까지 입력할 수 있다.
    2. 특수 문자
       - 가능: ( ), [ ], +, -, &, /, _
       - 그 외 특수 문자 사용 불가
    3. "카카오"가 포함된 문구가 입력되면, "담당 MD와 협의한 경우에만 사용할 수 있다"를 출력한다.
- 상품 가격(스스로 판단)
  1. 최소 100원~최대 1,000,000원까지 허용한다.
- 상품 url(스스로 판단)
  1. “http://” 혹은 “https://”로 시작하는 url 양식을 가져야 한다.

#### Controller
- REST API: 스프링 validation 활용하여 검증, ExceptionHandler를 통해 예외 처리
- 관리자 화면 렌더링: 스프링 validation 활용하여 검증, 오류 메시지 출력

#### Exception
- ProductException: 검증 오류 메시지를 포함한 커스텀 예외
- ProductExceptionHandler: 검증 오류 메시지를 포함하여 응답 메시지 반환

#### Validation
- 타입 에러: 스프링에서 기본으로 제공하는 typeMismatch를 메시지 커스텀
- bindingResult: 타입 에러 외 유효하지 않은 input들을 담아 Form html 전송

#### Html
- BindingResult.hasErrors() 시 에러를 출력하도록 수정
  - th:errors/errorclass를 활용하여 에러 출력

    
## 2단계
### 유저 인증을 통한 기능 사용 권한 검증
- 스프링 인터셉터로 유저 인증 기능 구현
    - 인증 방식: **JWT**
        - 유저 인증 후 토큰을 생성하여 응답, 이후 클라이언트의 요청이 들어오면 토큰 검증 후 요청 처리
    - 로그인된 유저인 경우에 한해서만 상품 조회/등록/수정/삭제가 가능하도록 허용
        - 비로그인된 유저의 경우는 상품 관련 작업 요청 불가능

#### Authorization
- Interceptor: http request를 가로채 유저 인증 작업을 수행, 토큰이 존재하지 않거나 유효하지 않으면 요청 차단
- Config: 인터셉터 등록, 허용/제외 경로 패턴 설정
- JWT 토큰 생성기

#### Controller
- ProductApiController에 한해 인증 기능 적용
- 회원가입 요청 API: api/join
  - 유저 가입이 정상적으로 수행되면, 토큰을 생성하여 정상 응답(로그인 처리)
  - 유저 가입이 실패할 시 예외 처리(유효하지 않은 입력, 중복된 이메일)
- 로그인 요청 API: api/login
    - 유저 인증이 정상적으로 수행되면, 토큰을 생성하여 정상 응답
    - 유저 인증이 실패할 시 예외 처리(유효하지 않은 입력, 존재하지 않는 회원)
- 상품 조회/생성/수정/삭제 API: api/products
    - 요청 시 인터셉터에서 토큰을 검증, 검증이 성공한 유저에 한해서 기능 사용을 허가
    - 검증에 실패한 유저는 접근 비허가 처리
  
#### Model
- Member: 유저 정보를 저장할 모델
- email: 유저 이메일(아이디), **중복 불가**
- password: 유저 패스워드

#### DB
- Member 테이블 생성


## 3단계
### 사용자별 위시 리스트 구현
- 사용자 인증을 완료한 후, 위시 리스트와 관련된 작업을 할 수 있음
- 사용자 인증: step2에서 구현한 토큰기반 사용자 인증 작업을 수행
- 위시 리스트 내 상품 조회/추가/삭제
    - 조회: 위시 리스트에 담긴 상품 목록 반환
    - 추가: 상품을 위시 리스트에 추가
    - 삭제: 위시 리스트에서 상품을 삭제

#### API
- member_id는 request body에 넣지 않음, 토큰을 디코딩 한 후 member_id에 접근할 수 있음
- 상품 조회 API: /api/products
- 상품 위시 리스트 추가 API: /api/wishlist
- 상품 위시 리스트 수량 변경 API: /api/wishlist
- 상품 위시 리스트 삭제 API: /api/wishlist 

#### Model
- WishProducts: 유저 위시리스트에 담긴 상품 모델
    - member_Id
    - product_Id

#### DB
- wish_products 테이블 생성