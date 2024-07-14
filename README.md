# spring-gift-jpa
## 기능 요구사항
### STEP 1
1. 엔티티 클래스와 리포지토리 클래스를 JPA로 리펙터링하여 작성한다
   - 현재 구현된 클래스에 `@Entity`를 사용하여 JPA에서 관리하는 엔티티 클래스로 만들어준다
   - `JpaRepository`를 상속하여 JPA 리파지토리를 만든다.
2. `@DataJpaTest`를 사용하여 테스트 코드를 작성한다.

### STEP 2
1. 각 Entity들의 연관 관계를 설정하고 관련 코드를 수정한다.
   - `Product`는 `User`와 다대일 관계로 연관관계의 주인이 된다.
     - `User`는 `Product`와 일대다 관계이다.
   - `Wish`는 `User`와 다대일 관계로 연관관계의 주인이 된다.
     - `User`는 `Wish`와 일대다 관계이다.
   - `Wish`는 `Product`와 다대일 관계로 연관관계의 주인이 된다.
2. Stpe1에서 작성한 학습 테스트를 수정한다.
