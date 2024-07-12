# spring-gift-jpa

### 1단계 기능 요구 사항
- 지금까지 작성한 JdbcTemplate 기반 코드를 JPA로 리팩터링하고 실제 도메인 모델을 어떻게 구성하고 객체와 테이블을 어떻게 매핑해야 하는지 알아본다.
  - [x] ProductRepository 생성
  - [x] MemberRepository 생성
  - [x] WishRepository 생성
  - [x] ProductDao에서 ProductRepository로 변경
    - [x] validate를 Product로 옮김.
  - [x] MemberDao에서 MemberRepository로 변경
  - [x] WishDao에서 WishRepository로 변경

@DataJpaTest를 사용하여 학습 테스트를 해 본다.

### 2단계 기능 요구 사항
객체의 참조와 테이블의 외래 키를 매핑해서 객체에서는 참조를 사용하고 테이블에서는 외래 키를 사용할 수 있도록 한다.
- [x] Wish Entity 수정
- [x] WishRepository 수정
- [x] Test 코드 수정

### 3단계 기능 요구 사항
상품과 위시 리스트 보기에 페이지네이션을 구현한다.
대부분의 게시판은 모든 게시글을 한 번에 표시하지 않고 여러 페이지로 나누어 표시한다. 정렬 방법을 설정하여 보고 싶은 정보의 우선 순위를 정할 수도 있다.
페이지네이션은 원하는 정렬 방법, 페이지 크기 및 페이지에 따라 정보를 전달하는 방법이다.
- [x] WishRepository Pageable 로 바꾸기
- [x] WishService Page 로 수정
- [x] WishController 수정
- [ ] ProductRepository Pageable 로 바꾸기
- [ ] ProductService Page 로 수정
- [ ] ProductController 수정
