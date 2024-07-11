# spring-gift-wishlist

---

## Step2

### 요구 사항
지금까지 작성한 JdbcTemplate 기반 코드를 JPA로 리팩터링하고 실제 도메인 모델을 어떻게 구성하고 객체와 테이블을 어떻게 매핑해야 하는지 알아본다.  
객체의 참조와 테이블의 외래 키를 매핑해서 객체에서는 참조를 사용하고 테이블에서는 외래 키를 사용할 수 있도록 한다.

### 리뷰 내용
> @Column(name = "name")  
> private Long value;  
> 🟢 바꾸실거면 변수명을 바꾸는게 더 낫지 않나요? 🤔
 
해당 객체인 ProductName 의 경우 변수명을 name 으로 하였지만,
Email 과 같은 몇몇 객체의 경우, 변수명을 email 로 설정하게되면  
`A field should not duplicate the name of its containing class`  
경고가 발생하게 됩니다. 때문에, 모든 변수명을 value 로 통일시키고, `@Column` 을 사용해주었습니다.
또한, Product가 아닌 다른 Table 에서 `name` column 을 만들게 될 경우나 클래스 이름을 수정하게 될 경우  
변수명과 실제 테이블 column 사이에서 일관성 문제가 발생할 수도 있다고 생각합니다.
그리고 value 로 통일하게 되면, Table 에서 Column명 수정이 훨씬 간편하다는 장점 또한 있습니다

> MyCrudRepository말고 JpaRepository 상속 받으면 똑같을 겁니다
 
JpaRepository를 그대로 상속 받게 되면, 사용 권한을 주고 싶지 않은 
`deleteInBatch`, `saveAll` 와 같은 함수들도 Service 에서 사용하게 될 수도 있다고 하여  
`@NoRepositoryBean` 을 적용시켜, 별도의 Repository를 만들어 보았습니다.

> hash Override가 조금 이상한데 다른 방식으로 정의해보시길 권합니다. 저러면 Equals나 HashMap 구조에서 문제가 생길 수 있어요!

```java
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductCount that = (ProductCount) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
```
이 방식에 정확히 어떠한 문제가 있는 지 모르겠습니다.
GPT 를 통해 물어본 결과, 내부 value 값이 바뀐다면 문제가 생긴다고 하는데,
setter 를 사용하지 않는다면, 문제가 되지 않는 것 아닐까요?
또는, setter 를 사용해도 문제가 없도록 선언할 수 있는 방법이 있는 걸까요?


### 구현 기능
이전 step1 에서 수정할 것들
- [x] equals and hashcode method 들 올바르게 수정하기
- [x] repository test 에서 findAll() 에 대한 테스트가 빠져있어, 추가하기

step2 에서 구현할 것들
- [x] 연관관계 매핑시키기
- [x] 아이디로 객체를 직접가져오는 방식에서 연관관계를 이용한 방식으로 수정하기
- [x] Member 가입 시, Email 과 Nickname 중복 불가능하도록 처리하기
- [x] Wish 추가 시, 이미 해당 Member 가 해당 Product 를 넣어두었다면, ProductCount 만큼 값을 추가하기
- [ ] Service test 작성하기






