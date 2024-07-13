# spring-gift-jpa

## step1

- JdbcTemplate 기반 코드를 JPA로 리팩터링
  - 기존 도메인을 앤티티로 변경
- @DataJpaTest를 사용하여 학습 테스트를 해 본다.
---
## step2

- user와 wish의 연관관계 설정
  - 객체의 참조와 테이블의 외래 키를 매핑해서 객체에서는 참조를 
  사용하고 테이블에서는 외래 키를 사용할 수 있도록 한다.