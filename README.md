# spring-gift-product

## 기능 요구 사항
- 상품을 조회, 추가, 수정, 삭제할 수 있는 간단한 HTTP API를 구현한다.
- HTTP 요청과 응답은 JSON 형식으로 주고받는다.
- 현재는 별도의 데이터베이스가 없으므로 적절한 자바 컬렉션 프레임워크를 사용하여 메모리에 저장한다.

## 기능 목록
- Controller
  - get
  - add
  - update
  - delete
- Entity
  - product
- DTO
  - RequestDTO
  - ResponseDTO

- 관리자 화면 구현
  - 메인 페이지
    - getAll
  - 상품 입력 폼
    - 상품명, 상품가격, 상품url
  - 상품 선택 후 삭제
- 메모리에 저장하고 있던 모든 코드를 제거하고 H2 데이터베이스를 사용하도록 변경한다.
- 사용하는 테이블은 애플리케이션이 실행될 때 구축되어야 한다.

# spring-gift-wishlist

