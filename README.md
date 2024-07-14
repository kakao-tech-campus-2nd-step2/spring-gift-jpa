# spring-gift-jpa
# step1
- [x] JdbcTemplate 기반 코드를 JPA로 리팩터링
  - [x] product pakage 리팩터링
  - [x] user pakage 리팩터링
- [x]@DataJpaTest를 사용하여 학습 테스트를 해 본다.
  - [x] ProductRepositoryTest 작성
  - [x] MemberRepositoryTest 작성
# step2
- [x] Entity에서 setter 호출하는 부분 최소화
- [x] 사용자 별 위시리스트 구현 (2주차 step3)
  - [x] 위시리스트에 등록된 상품 목록을 조회
  - [x] 위시리스트에 상품 추가
  - [x] 위시리스트에 상품 삭제
  - [x] 위시리스트 상품 수량 변경
- [x] 로그인 (2주차 step3)
  - [x] 헤더에 있는 토큰으로 사용자 파악
    - [x] Interceptor를 통해 사용자 인증
    - [x] HandlerMethodArgumentResolver를 통해 사용자 정보를 컨트롤러에 전달
- [x] 객체의 참조와 테이블의 외래 키를 매핑해서 객체에서는 참조를 사용하고 테이블에서는 외래 키를 사용할 수 있도록 한다.
  - [x] Product 와 Wishlist 연관 관계 매핑
  - [x] Member 와 Wishlist 연관 관계 매핑
# step3
- [x] pagination 구현
- [x] pagination Test 작성
# 피드백
- [ ] Product에 대한 단위 테스트
- [ ] 외부에서 사용하지 않는 Entity 기본 생성자 protected로 변경
- [ ] @kakao의 유효성 검사