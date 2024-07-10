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