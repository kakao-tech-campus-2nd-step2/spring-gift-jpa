# spring-gift-product
### Step 1. 상품 API
- GET 요청을 통한 **정보 조회 기능** 구현
- 필요한 사항
    1. 상품의 정보를 담을 DTO 클래스
    2. 상품들을 저장할 HashMap
    3. 위의 정보를 Json 형식으로 반환해줄 Controller 클래스
- 비고
    - 현재는 별도의 데이터베이스가 없으므로 적절한 컬렉션을 이용하여 메모리에 저장한다.
- **추가**
    - CRUD 모두를 구현해야 한다!!!
### Step 2. 관리자 페이지
- CRUD 기능을 수행하는 **관리자 페이지** 구현
- 필요한 사항
    1. Read를 제외한 Create, Update, Delete API 구현
    2. HTTP 요청을 보낼 HTML Form 작성
        - Form은 Create, Upadate에 대해서만 있으면 된다
    3. 이전에 만든 객체 전송 사양을 View로 바꾸어야 한다
        - 기존 : @RestController를 이용하여 객체를 반환
        - 신규 : @Controller를 이용하여 View에 정보를 전달하고 이를 반환
### Step 3. 데이터베이스 적용
- 기존에 메모리에 저장되는 HashMap에 저장하던 방식을 넘어, 데이터베이스에 정보를 저장
- 필요한 사항
    1. 서버 구동 시, 데이터베이스 초기화 및 테이블 구축
    2. 데이터베이스는 h2를 사용
    3. Create -> INSERT / Update -> UPDATE 등, 매칭되는 DB 쿼리문을 사용

# spring-gift-wishlist
### Step 0. 기본 코드 준비
- 기존에 PR로 피드백 받은 부분에 대한 코드로 프로젝트 초기화
### Step 1. 유효성 검사 및 예외 처리
- 상품 이름에 대한 유효성 검사와 예외 처리 구현
- 필요한 사항
    1. 상품 이름의 길이는 15자 제한
    2. 상품 이름에 사용할 수 있는 특수문자는 ( ), [ ], +, -, &, /, _로 제한
    3. '카카오'가 포함된 문구는 담당 MD와 협의한 경우에만 사용
        - 다른 부분과 다르게 표시해줘야 한다
    4. 추가 사항 : DTO, Domain model 클래스의 분리, Repository-Service-Controller 구조로의 변경
### Step 2. 회원 로그인
- JWT를 이용한 회원 검증 기능 구현
- 필요한 사항
    1. 회원 정보를 담을 DTO, Domain model 클래스 구현
    2. 회원 정보를 다룰 Repository, Service, Controller 클래스 구현
    3. 정보를 주고 받을 View 구성
    4. JWT 토큰 생성 기능 구현
    5. 생성한 토큰을 주고 받을 전송 로직 구현
    6. 회원 관리 화면 및 기능 구현 ( 선택 )
### Step 3. 위시 리스트
- JWT를 이용한 회원 인식을 통해 회원별 위시리스트 생성 및 관리
- 필요한 사항
    1. 회원 로그인 시 페이지 변경 (로그인/회원가입 -> 위시리스트/로그아웃)
        - 로그아웃시 localStorage 에서 토큰 삭제
        - 위시리스트 클릭 시 회원 별 위시리스트 화면으로 전환
    2. 로그인 성공 시 토큰을 지급하고, 로그인을 해야만 할 수 있는 요청에 대해 Authorization: Bearer token 헤더로 검증
    3. 회원 별 위시 리스트를 구현하기 위해 DB 테이블 및 DTO, Domain, Repository, Service, Controller 클래스 구현
    4. 회원 별로 위시 리스트를 보여주는 View page 구현

# spring-gift-jpa
### step 0. 기본 코드 준비
- 새로운 리포지토리를 fork 해와 이전 코드를 붙여 넣기
### step 1. 엔티티 매핑
- JPA를 이용하여 엔티티를 DB의 테이블과 매핑시키기
- 필요한 사항
    1. 코드 리펙터링 : Domain 객체를 엔티티로 사용
    2. 코드 리펙터링 : Spring Application 동작 시 수행되던 table 생성 로직 삭제
    3. 코드 리펙터링 : Repository 클래스 내용 JPA에 맞게 변경

### step 2. 연관 관계 매핑
- JPA를 이용하여 객체의 참조와 테이블의 Foreign 키를 매핑하기
- 필요한 사항
    1. 코드 리펙터링 : WishProduct 엔티티의 필드 변경 id-> 실제 클래스 객체
    2. 코드 리펙터링 : 해당 변경에 맞게 DAO 클래스와 Service, Controller 모두 변경
- 추가 사항
    - DTO 클래스의 Response와 Request 분리

### step 3. 페이지네이션
- Spring data의 Pageable 객체를 사용하여 findAll 명령에 대한 응답을 Page 단위로 끊어서 받기
  - 필요한 사항
    1. 코드 리펙터링 : Repository 클래스의 메서드 반환값 변경 : List<Object> -> Page<Object>
    2. 코드 리펙터링 : Service 클래스의 메서드 로직 변경 : 매개변수 - page (페이지 수), size (페이지 하나에 들어갈 데이터 수)
       - 해당 정보들로 Pageable 객체를 생성하여 Repository 객체의 매개변수로 사용
    3. 코드 리펙터링 : Controller 클래스의 get 요청 메서드 매개변수를 page, size로 변경 
       - 반환 값은 List<Object> 형태에서 Page<Object> 형태로 변하고 이를 가져다가 Front에 적용하여 페이지네이션 구현!
    4. 페이지 형식을 적용한 View 만들기