# spring-gift-jpa

## 기능 요구 사항

지금까지 작성한 JdbcTemplate 기반 코드를 JPA로 리팩터링하고 실제 도메인 모델을 어떻게 구성하고 객체와 테이블을 어떻게 매핑해야 하는지 알아본다.

- 객체의 참조와 테이블의 외래 키를 매핑해서 객체에서는 참조를 사용하고 테이블에서는 외래 키를 사용할 수 있도록 한다.

### 힌트

- 이전 단계에서 엔티티 설계가 이상하다는 생각이 들었다면 객체 지향 설계를 의식하는 개발자고, 그렇지 않고 자연스러웠다면 데이터 중심의 개발자일 것이다. 객체 지향 설계는 각각의
  객체가 맡은 역할과 책임이 있고 관련 있는 객체끼리 참조하도록 설계해야 한다.

``` java
Question question = findQuestionById(questionId);
List<Answer> answers = answerRepository.findByQuestionIdAndDeletedFalse(questionId);
```

- 위 방식은 객체 설계를 테이블 설계에 맞춘 방법이다. 특히 테이블의 외래 키를 객체에 그대로 가져온 부분이 문제다. 왜냐하면 관계형 데이터베이스는 연관된 객체를 찾을 때 외래
  키를 사용해서 조인하면 되지만 객체에는 조인이라는 기능이 없다. 객체는 연관된 객체를 찾을 때 참조를 사용해야 한다.

``` java
Question question = findQuestionById(questionId);
List<Answer> answers = question.getAnswers();
```

- 아래의 DDL을 보고 유추한다.
- H2

``` sql
alter table if exists wish
add constraint fk_wish_member_id_ref_member_id
foreign key (member_id)
references member

alter table if exists wish
add constraint fk_wish_product_id_ref_product_id
foreign key (product_id)
references product
```

- MySQL

``` sql
alter table wish
add constraint fk_wish_member_id_ref_member_id
foreign key (member_id)
references member (id)

alter table wish
add constraint fk_wish_product_id_ref_product_id
foreign key (product_id)
references product (id)
```

## 구현기능 목록

1. Wishlist테이블에서 Product를 참조할 수 있게 연관 관계를 매핑한다.
1. Wishlist테이블에서 Member를 참조할 수 있게 연관 관계를 매핑한다.