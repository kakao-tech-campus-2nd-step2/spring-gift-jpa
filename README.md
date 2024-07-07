# [구현할 기능 목록] 0단계 - 기본 코드 준비 
- [x] 위시 리스트 저장소를 포크한 후 클론하여 로컬로 가져온다.
- [x] 이전 주차 과제를 수행한 저장소인 spring-gift-product를 원격 레포지토리 추가한다.
- [x] spring-gift-product의 stpe1 브랜치를 spring-gift-wishlist의 ste0 브랜치로 병합한다.
- [x] 병합 후 spring-gift-product 원격 레포지토리 제거한다. 
- [x] 리소스 파일, 프로퍼티 파일, 테스트 코드 등이 함꼐 이동되었는지 확인한다.

--- 

# [구현할 기능 목록] 1단계 - 유효성 검사 및 예외 처리
- 잘못된 값이 전달되면 클라이언트가 어떤 부분이 왜 잘못되었는지 인지할 수 있도록 응답을 제공한다.

- [x] spring-boot-starter-validation 의존성을 명시적으로 추가
1. 추가/수정하려는 상품에 대한 유효성 검사 및 예외 처리
- [x] 상품의 필드값들은 필수 입력 값이다.
- [x] 가격은 0 이상의 정수만 입력할 수 있다.
- [x] 상품 이름은 공백을 포함하여 최대 15자까지 입력할 수 있다.
- [x] 특수 문자 가능: ( ), [ ], +, -, &, /, _ 그 외 특수 문자 사용 불가
- [x] "카카오"가 포함된 문구는 담당 MD와 협의한 경우에만 사용할 수 있다.
2. 전체적인 리팩토링
- [x] 디렉토리 재설정 및 코드 가독성 향상
- [x] repository - 인터페이스와 구현체 나누기
--- 
# [구현할 기능 목록] 2단계 - 인증
### 사용자가 회원 가입, 로그인, 추후 회원별 기능을 이용할 수 있도록 구현한다. JSON Web Token 방법을 사용할 것이다.
## 0. 라이브러리 추가 및 환경 설정
- [x] JWT 라이브러리 추가 및 설정
## 1. 회원가입 로직 구현
### (1) Model 설계
- [x] request, response 객체 생성
### (2) Service 설계
- [x] 회원을 등록 로직 구현
- [x] secrete_key를 통해 jwt 토큰을 발급한다.
### (3) Repository 설계
- [x] jdbc를 이용해 db에 회원 정보 등록
### (4) Controller 설계
- [x] 회원가입 요청을 받아 서비스에 전달
- [x] 토큰값 반환
## 2. 로그인 로직 구현
- 사용자는 acceess token을 받으려면 이메일과 비밀번호를 보내야 하며, 가입한 이메일과 비밀번호가 일치하면 token이 발급된다.
### (1) Model 설계
- [x] 로그인 요청을 담은 객체 생성
### (2) Service 설계
- [x] 로그인 로직 구현
### (3) Controller 설계
- [x] 로그인 요청을 받아 서비스에 전달
### 3. 예외처리
- Authorization 헤더가 유효하지 않거나 토큰이 유효하지 않은 경우 401 Unauthorized를 반환한다.
- 잘못된 로그인, 비밀번호 찾기, 비밀번호 변경 요청은 403 Forbidden을 반환한다.
--- 
# [구현할 기능 목록] 3단계 - 위시 리스트
#### 이전 단계에서 로그인 후 받은 토큰을 사용하여 사용자별 위시 리스트 기능을 구현한다.
#### 단, 사용자 정보는 요청 헤더의 Authorization 필드를 사용한다.
#### ex) Authorization: Bearer token
### 1. HandlerMethodArgumentResolver 구현
#### (1) `LoginMember` 어노테이션 생성
- [x] 컨트롤러 메서드의 파라미터에 붙여서 로그인한 사용자의 정보를 주입하기 위해 사용됨
#### (2) `JwtUtil` 구현
- [x] 토큰 유효성 검증
- [x] 토큰에서 이메일 정보 추출
- [x] JWT 토큰을 파싱하여 그 안에 포함된 클레임을 반환
#### (3) `LoginMemberArgumentResolver` 구현
- [x] `supportsParameter` : `LoginMember` 어노테이션을 가진 파라미터를 지원하는지 판단
- [x] `resolveArgument` : 토큰값 인증 후 사용자 정보 반환
- [x] `WebConfig`에 `LoginMemberArgumentResolver` 추가
### 2. 위시 리스트 CRUD 기능 구현
#### (1) `WishList` 모델 설계 및 테이블 설정
- [x] 상품 정보를 담을 `Product` 모델 설계
- [x] 멤버id와 상품들을 담을 `WishList` 모델 설계
- [x] `WishList` 테이블 생성
#### (2) `WishListController` 구현
- [x] 위시 리스트 추가 기능 구현
- [x] 위시 리스트 조회 기능 구현
- [x] 위시 리스트 수정 기능 구현
- [x] 위시 리스트 삭제 기능 구현
#### (3) `WishListService` 구현
#### (4) `WishListRepository` 구현



