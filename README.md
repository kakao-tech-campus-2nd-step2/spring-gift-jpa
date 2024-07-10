# spring-gift-jpa
## 기능 요구사항
### STEP 1
1. 엔티티 클래스와 리포지토리 클래스를 JPA로 리펙터링하여 작성한다
   - 현재 구현된 클래스에 `@Entity`를 사용하여 JPA에서 관리하는 엔티티 클래스로 만들어준다
   - `JpaRepository`를 상속하여 JPA 리파지토리를 만든다.
2. `@DataJpaTest`를 사용하여 테스트 코드를 작성한다.
