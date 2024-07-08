# spring-gift-wishlist

## 3단계 - 위시 리스트 기능 요구사항
이전 단계에서 로그인 후 받은 토큰을 사용하여 사용자별 위시 리스트 기능을 구현한다.
- 위시 리스트에 등록된 상품 목록을 조회할 수 있다.
- 위시 리스트에 상품을 추가, 삭제할 수 있다.

### HandlerMethodArgumentResolver
컨트롤러 메서드에 진입하기 전처리를 통해 객체를 주입할 수 있다.

### JWT 생성 방법
SignatureAlgorithm 클래스 사용


## 2단계 - 회원 로그인 기능 요구사항
사용자가 회원 가입, 로그인, 추후 회원별 기능을 이용할 수 있도록 구현한다.
- 회원은 이메일과 비밀번호를 입력하여 가입한다.
- 토큰을 받으려면 이메일과 비밀번호를 보내야 하며, 가입한 이메일과 비밀번호가 일치하면 토큰이 발급된다.
- 토큰을 생성하는 방법에는 여러 가지가 있다. 방법 중 하나를 선택한다.
- (시간되면) joinForm.html 을 만들어 컨트롤러와 타임리프를 사용하여 연결한다.


## 1단계 - 유효성 검사 및 예외 처리 기능 요구사항
상품을 추가하거나 수정하는 경우, 클라이언트로부터 잘못된 값이 전달될 수 있다. 
잘못된 값이 전달되면 클라이언트가 어떤 부분이 왜 잘못되었는지 인지할 수 있도록 응답을 제공한다.
- 상품 이름은 공백을 포함하여 최대 15자까지 입력할 수 있다.
- 특수 문자 
      가능: ( ), [ ], +, -, &, /, _
      그 외 특수 문자 사용 불가
- "카카오"가 포함된 문구는 담당 MD와 협의한 경우에만 사용할 수 있다. -> 일단 메시지만 출력하도록 구현한다.

### validation
spring-boot-starter-validation 의존성을 명시적으로 추가한다.
`implementation 'spring-boot-starter-validation'`




## 3주차부터 -> 아래와 같이 기능별 보다 객체별로 구조를 나누면 어떤가요? 아니면 또 한번, 기능별로 구분할까요?
```plaintext
└── src
    └── main
        ├── java
        │   └── gift
        │       ├── Application.java
        │       │
        │       ├── admin
        │       │   └── AdminController.java
        │       │ 
        │       ├── exception
        │       │   ├── ForbiddenException.java
        │       │   ├── UnauthorizedException.java
        │       │   ├── KakaoProductException.java
        │       │   └── GlobalExceptionHandler.java
        │       │ 
        │       ├── member
        │       │   ├── Member.java
        │       │   ├── MemberController.java
        │       │   ├── MemberDto.java
        │       │   ├── MemberRepository.java
        │       │   ├── MemberService.java
        │       │	└── TokenService.java
        │       │
        │       ├── product
        │       │   ├── product.java
        │       │   ├── ProductController.java
        │       │   ├── ProductDao.java
        │       │   ├── ProductDto.java
        │       │   ├── ProductModel.java
        │       │   ├── ProductName.java
        │       │   └── ProductService.java
        │       │
        │       └── wishlist
        │           ├── WishList.java
        │           ├── WishListController.java
        │           ├── WishListRepository.java
        │           └── WishListService.java
        │       
        └── resources
            ├── member.html
            ├── data.sql
            ├── schema.sql
            ├── static
            └── templates
                ├── add.html
                ├── edit.html
                ├── list.html
                └── view.html