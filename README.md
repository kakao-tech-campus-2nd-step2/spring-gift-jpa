# spring-gift-jpa

### step1

JPA로 리팩토링하고 @DataJpaTest를 사용해 학습테스트를 해본다.

1. Entity mapping : Member, Product, Wish.java 리팩토링
2. Repository JPA : MemberRepository, ProductRepository, WishRepository
3. 테스트 코드 : MemberRepositoryTest, ProductRepositoryTest, WishRepositoryTest

### step2

1. Entity 간 연관관계 Mapping
- Member는 여러 개의 Wish를 가질 수 있다. -> OneToMany
- Product는 여러 개의 Wish에 포함될 수 있다. -> OneToMany
- Wish는 하나의 Member와 하나의 Product에 Mapping -> ManyToOne

2. WishResponse.java 추가
3. setters 대신 Builder pattern 적용

