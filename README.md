# spring-gift-jpa

# 3주차 3단계 목표

페이지네이션 구현하기 

### 기능 목록
- [ ] 상품보기에 페이지네이션을 구현한다.
- [ ] 위시 리스트 보기에 페이지네이션을 구현한다.
- [ ] 정렬 방법을 설정하여 보고 싶은 정보의 우선 순위를 정할 수도 있다.

- 스프링 데이터는 Pageable이라는 객체를 제공하여 쉽게 구현할 수 있다. 
- 또한 List, Slice, Page 등 다양한 반환 타입을 제공한다.

# 3주차 2단계 목표 

객체의 참조와 테이블의 외래 키를 매핑해서 
객체에서는 참조를 사용하고 
테이블에서는 외래 키를 사용할 수 있도록 한다.

### 기능목록 

- [x] 엔티티간 연관관계 맵핑 
  - [x] 객체 참조 구현 
  - [x] 테이블 외래키 사용하도록 구현 

# 3주차 1단계 목표
## 지금까지 작성한 JdbcTemplate 기반 코드를 JPA로 리팩터링
실제 도메인 모델을 어떻게 구성하고 객체와 테이블을 어떻게 매핑해야 하는지 알아본다

## 기능 목록 

- [x] repository 에 JpaRepository extends
- [x] jpa로 entitiy 와 db 테이블 매핑 
- [x] 서비스 테이블 수정

- []  @DataJpaTest 사용해서 학습테스트

- [x] Users 테이블 
- [x] Products 테이블 
- [x] Wishes 테이블 
- [x] Token 테이블 


### 2주차 3단계 요구사항 수정

## 3단계 위시리스트 구현

### 고려할점
[V] 회원과 위시리스트 상품 간의 관계
회원은 여러 종류의 상품을 위시리스트에 넣을 수 있다.
한 상품은 여러명의 회원의 위시리스트에 소속될 수 있다.
현재 회원 id 대신 토큰값을 저장하고 있기 때문에, 토큰은 로그아웃 이후에도 유지되며, 한 회원당 하나로 불변 한다고 가정
=> 토큰대신 회원에게 부여된 id 값을 저장하는것으로 변경
=> Token 테이블 필요

[V] 요청헤더에 Authorization 필드에 사용자 정보 저장
Authorization: <유형> <자격증명> ex) Authorization: Bearer token

### 전처리 
[V] 위시리스트를 담을 테이블 생성
* 위시리스트의 id 
* 상품 id (fk - products 테이블의 id )
* 위시리스트 소유자(회원)의 id (fk - user 테이블의 id)

[V] 토큰을 저장할 테이블 생성 
* 토큰의 id
* 토큰 값 (String)
=> 토큰 소유자의 id 는 굳이 넣지 않았습니다.
 * 이유 1 : 토큰값을 해독하면 알수있는정보라 중복발생
 * 이유 2 : 보안 취약성은 id 넣으나 안넣으나 거의 유사하나, id 넣은게 조금더 취약할것
   * -> 토큰이 탈취된경우, 해독시, 어떤 방식으로 인코딩 했는지 모르면 해독이 어려운데, 아이디값을 함께저장해버리는 엔티티가 되어버리면 해독과정 없이 id 알아낼수있음 
   * 궁금한사항 (추가로 알아볼것): auto increment 되는 id 값으로 토큰을 만드는게  유저가 입력한 id 를 가지고 토큰을 만드는 것보다 더 안전할것 같다는 생각? 
 
### 기능목록 
* 토큰 
    - [x] 토큰 entity 만들기
      - [ ] user id 로 토큰 생성해서 토큰 value에 넣는 메서드 
            * Basic 방식 (Base64)
      - [ ] 토큰 value 값 가져오는 getter 

  - [ ] 토큰 Dto
    - [] 토큰 value 값만 필드로 갖는 클래스 

* 위시리스트
- [ ] 위시리스트에 상품 추가 기능
 * 현재 로그인한 회원의 id 와, 선택한 상품 id를 가져와서 함께 저장

- [ ] 위시리스트 상품 조회 기능
 * 현재 로그인한 회원의 토큰[회원id]에서 회원의 id를 디코딩해서 얻고, 
 * 위시리스트에 담긴 회원 id와 비교해서 같은 경우 전체에 대하여
 * 해당 위시리스트 상품의 id를 모두 가져오고
 * products 테이블에서 해당 상품id 에 해당하는 상품들의 이름, 가격 , url 정보를 반환 한다.

- [ ] 위시리스트의 상품 삭제 기능
  * 회원 토큰과 삭제할 상품 id 를 입력값으로 받아서, 
  * 현재 로그인한 회원의 토큰[회원id]에서 회원의 id를 디코딩해서 얻고,
  * wishes 테이블에서 해당 회원 id 를 가진 레코드중에서 선택한 상품 id 가 존재하는 경우
  * 해당 상품을 테이블에서 삭제


