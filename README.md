# spring-gift-product

1주차
step-1의 구현 사항
0. 데이터베이스 : 별도의 데이터베이스가 없으므로 컬렉션인 HashMap 사용
1. 상품을 추가, 삭제, 수정, 조회할 수 있는 HTTP API 구현
   1. 추가 가능 : Post에 해당하며, Product를 입력받아 HashMap에 저장한다.
   2. 삭제 기능 : Delete에 해당하며, key값을 입력받아 HashMap에서 제거한다.
   3. 수정 기능 : Put에 해당하며, key값과 Product를 입력받아 HashMap에 업데이트한다.
   4. 조회 기능 : Get에 해당하며, HashMap에 있는 모든 데이터를 불러오거나, 특정 key값에 해당하는 데이터를 불러온다.

step-2의 구현 사항
1. thymeleaf를 이용한 관리자 페이지 구현
   1. 조회 시 사용할 products.html의 구현(id, name, price, imageUrl을 컨트롤러에서 받아 테이블 형태로 화면에 출력)
   2. products.html에 사용할 css파일(style.css) 구현
   3. 조회 기능을 담당하는 메소드에서 product.html을 사용하도록 연결
   4. 추가 기능을 웹으로 구현하기 위해 add.html 및 menu.css 추가
   5. 수정 기능을 웹으로 구현하기 위해 edit.html 및 menu.css 추가
   6. 웹 화면에서 추가, 삭제, 수정, 조회 기능을 담당하는 adminController 클래스 추가
2. step-1에서 미흡한 사항 보완
   1. json형태로 통신하는 것을 명시하기 위해 추가, 삭제, 수정을 담당하는 메소드에 @RequestBody 어노테이션 추가
   2. 추가, 삭제, 수정 시 완료 여부를 String 값으로 리턴 => ResponseEntity형태로 변경, Status와 Body를 추가하여 리턴하며, 기존의 String 내용은 Body에 담음.

step-3의 구현 사항
1. HashMap을 Database로 교체
   1. Database를 사용하기 위해 메모리에 데이터를 저장하는 h2 Database를 사용
   2. 기존의 HashMap은 제거하고, ProductDAO(Data Access Object)를 만들어 사용
   3. ProductDAO에는 기존의 HashMap에서 지원하는 추가, 삭제, 수정, 조회 기능을 구현해야 함
      0. 데이터베이스 생성 : sql구문 및 jdbcTemplate의 execute메소드 를 통해 생성
      1. 추가 기능 : 파라미터로 Product를 받으며, sql구문 및 jdbcTemplate의 update 메소드를 통해 추가
      2. 삭제 기능 : 파라미터로 Long id를 받으며, sql구문 및 jdbcTemplate의 update 메소드를 통해 삭제
      2. 수정 기능 : 파라미터로 Long id 및 Product를 받으며, sql구문 및 jdbcTemplate의 update 메소드를 통해 수정
      3. 조회 기능 : 전체 데이터를 조회할 경우 query를 통해 받으며, 파라미터로 Long id를 입력받아 queryForObject를 이용하여 받음
   4. 기타 기능
      1. url, user, password를 빠르게 입력하기 위하여 getConnection() 구현
      2. application.properties에서 url을 "jdbc:h2:mem:test"으로 지정
2. step-2에서 미흡한 사항 보완
   1. ProductController에서 추가, 삭제, 수정 기능에서 성공 여부 판별을 위해 if문 사용 : Database 사용 시 다시 조회해서 확인해야하는 불편함 있음
      => try-catch로 변경하고 에러 발생 시 실패, 에러가 발생하지 않을 경우 성공

2주차

1. step-1의 구현사항
   1. 상품 추가 및 수정 시 잘못된 값 판별
      1. 상품 이름은 공백을 포함하여 최대 15글자까지 입력 가능
         => @Size(max=15) 어노테이션 사용
      2. 특수문자 '( ), [ ], +, -, &, /, _'만 사용 가능
         => @Pattern 어노테이션에서 정규식 표현을 사용
      3. '카카오'가 포함된 문구는 담당 MD와 협의한 경우에만 사용 가능
         => @Pattern 어노테이션에서 정규식 표현을 사용
2. step-2의 구현사항
   ~~1. email과 password를 POST
   => String email, String password를 포함하는 UserInfo 클래스 생성, UserInfo를 저장하는 UserInfoDAO 클래스 생성
   2. accessToken을 return
      => accessToken 및 TokenType을 가지는 JwtToken 클래스 생성
      => 토큰을 생성하기 위해 jjwt 라이브러리 사용
      => Database에 있는 값과 일치할 경우에 토큰을 생성하여 리턴하는 LoginController 클래스 추가~~
   1. 회원가입
      - @PostMapping("/memebers/register")과 매핑, 테이블에 email값이 포함된 데이터를 카운트해서 0이면 생성, 토큰을 리턴한다.
      - 실패 시 RegisterException을 throw하며, 403 Forbidden 리턴
   2. 로그인
      - @PostMapping("/memebers/login")과 매핑, 테이블에 email값이 포함된 데이터를 찾아서 비밀번호가 일치하면 토큰을 리턴한다.
      - 실패 시 LoginException을 throw하며, 403 Forbidden 리턴
3. step-3의 구현사항
   1. 로그인 후 받은 토큰을 사용하여 사용자별 위시 리스트를 구현한다.
      => 토큰을 파싱하여 이메일 및 역할에 따라 접근 가능 여부를 판단
   2. 위시 리스트에 있는 상품을 조회할 수 있다.
      => 위시 리스트 데이터베이스에서 이메일이 일치하는 상품만 리턴한다.
   3. 위시 리스트에 상품을 추가할 수 있다.
      => 위시 리스트 데이터베이스에 추가한다.
   4. 위시 리스트에 담긴 상품을 삭제할 수 있다.
      => 위시 리스트 데이터베이스에 있을 경우 삭제한다.