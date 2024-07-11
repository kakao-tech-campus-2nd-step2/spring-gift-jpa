# spring-gift-jpa

# 진행 방식
- 미션은 과제 진행 요구 사항, 기능 요구 사항, 프로그래밍 요구 사항 세 가지로 구성되어 있다.
- 세 개의 요구 사항을 만족하기 위해 노력한다. 특히 기능을 구현하기 전에 기능 목록을 만들고, 기능 단위로 커밋 하는 방식으로 진행한다.
- 기능 요구 사항에 기재되지 않은 내용은 스스로 판단하여 구현한다.

# 과제 진행 요구 사항
- 기능을 구현하기 전 README.md에 구현할 기능 목록을 정리해 추가한다.
- Git의 커밋 단위는 앞 단계에서 README.md에 정리한 기능 목록 단위로 추가한다.
- AngularJS Git Commit Message Conventions을 참고해 커밋 메시지를 작성한다.

# 기능 요구 사항
- 지금까지 작성한 JdbcTemplate 기반 코드를 JPA로 리팩터링하고 실제 도메인 모델을 어떻게 구성하고 객체와 테이블을 어떻게 매핑해야 하는지 알아본다.

- 객체의 참조와 테이블의 외래 키를 매핑해서 객체에서는 참조를 사용하고 테이블에서는 외래 키를 사용할 수 있도록 한다.

# 힌트
- 객체 지향 설계는 각각의 객체가 맡은 역할과 책임이 있고 관련 있는 객체끼리 참조하도록 설계해야 한다.

```
Question question = findQuestionById(questionId);
List<Answer> answers = answerRepository.findByQuestionIdAndDeletedFalse(questionId);
```
- 위 방식은 객체 설계를 테이블 설계에 맞춘 방법이다.
- 특히 테이블의 외래 키를 객체에 그대로 가져온 부분이 문제다.
- 왜냐하면 관계형 데이터베이스는 연관된 객체를 찾을 때 외래 키를 사용해서 조인하면 되지만 객체에는 조인이라는 기능이 없다.
- 객체는 연관된 객체를 찾을 때 참조를 사용해야 한다.

```
Question question = findQuestionById(questionId);
List<Answer> answers = question.getAnswers();
```
- 아래의 DDL을 보고 유추한다.

# H2
```
alter table if exists wish
add constraint fk_wish_member_id_ref_member_id
foreign key (member_id)
references member
```
```
alter table if exists wish
add constraint fk_wish_product_id_ref_product_id
foreign key (product_id)
references product`

```
