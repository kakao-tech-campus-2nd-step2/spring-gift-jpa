# spring-gift-jpa

## 1단계
### JdbcTemplate → JPA로 리팩토링

- DAO 기술을 JdbcTemplate에서 Hibernate JPA로 변경한다.
- 새로운 데이터 액세스 기술에 맞춰 Model과 Repository를 재설계한다.
- 변경 후 API가 정상적으로 동작하는지를 테스트한다.

### Model

#### JPA를 활용하여 매핑 정보 설정

- Member
- Product
- WishProduct

### Repository

#### Spring Data JPA를 활용한 인터페이스 타입 Repository 구현

- MemberRepository
- ProductRepository
- WishProductRepository

### Testing

#### 통합 테스팅 - 각 Repository의 API들이 정상적으로 수행되는지 테스트

- MemberRepositoryTest
- ProductRepositoryTest
- WishProductRepositoryTest

#### E2E 테스팅 - request~reponse까지 정상적인 흐름대로 수행되는지 테스트

- ProductApiControllerTest
- WishListApiControllerTest

## 2단계
### WishProduct 연관 관계 매핑

#### 기존 WishProduct

- 멤버 변수로 memberId와 productId를 받았다.
- WishProduct에서 유저나 상품을 조회하려면, 별도의 도메인 객체를 생성하여 해당 도메인의 repo로부터 findById로 데이터를 조회해야 한다.
    - WishProduct와 별도로 생성한 참조 객체는 별개이므로, 둘을 함께 관리하기가 어려움

#### 새로운 WishProduct

- WishProduct의 멤버 변수로 member 도메인 객체와 product 도메인 객체를 가지게 한다.
    - @ManyToOne: WishProduct 내부의 멤버 변수인 Product/Member와 MemberProduct 사이에 다대일 관계가 성립함을 명시한다.
    - @JoinColumn: 외래키의 컬럼명을 명시하여 조인 조건을 설정한다.

#### Service, Controller, Repository

- WishProduct 내에 Member/Product id값 참조에 대한 리팩토링
    - wishproduct.getMemberId() → wishproduct.getMember.getId()

## 3단계
### 상품 목록과 위시리스트 목록에 페이징 구현
- 상품 목록 View와 위시리스트 목록 View 페이징을 구현하여, 페이지 별 목록 조회를 가능하게 한다.

#### Controller
- ProductViewController: 상품 목록 조회, 위시리스트 상품 추가
- WishViewController: 위시리스트 목록 조회, 위시리스트 내 상품 삭제
  - 회원가입/로그인 후 나의 위시 리스트에 담긴 상품을 조회하거나 삭제할 수 있다.

#### View
- /view/join: 회원 가입
- /view/login: 로그인
- /view/products: 메인 화면, 상품 조회
  - /view/wish/add: 상품 위시리스트에 추가
- /view/wish: 위시 리스트 조회(유저 인증 필요)
  - /view/wish/delete: 위시리스트 삭제
