# spring-gift-jpa

### step0

- 위시리스트 작업물 세팅

### step1

- 필요 의존성 추가 및 properties 추가
- 각 entity JPA로 변환
- Repository class 생성 및 구현
- @DataJpaTest를 이용한 Test생성 및 구현

### step2

- entity들 연관관계 매핑
- 매핑된 연관관계 기반 외래 키 사용으로 코드 수정
- Test코드 수정 및 테스트

### step3

- JPA repository에 Page타입의 find JPQL 추가
- service와 repository단 모든 객체 표시 반환 타입을 List -> Page로 변경
- test를 위한 더미 데이터 추가
- test코드 추가
