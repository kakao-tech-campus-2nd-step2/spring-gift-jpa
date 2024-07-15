# [구현할 기능 목록] 3단계 - 페이지네이션 
- 상품과 위시 리스트 보기에 페이지네이션을 구현한다.
- 대부분의 게시판은 모든 게시글을 한 번에 표시하지 않고 여러 페이지로 나누어 표시한다. 정렬 방법을 설정하여 보고 싶은 정보의 우선 순위를 정할 수도 있다.
- 페이지네이션은 원하는 정렬 방법, 페이지 크기 및 페이지에 따라 정보를 전달하는 방법이다.
### 1. `Pageable` 객체를 활용하여 페이지네이션을 구현
- [x] wish list 페이지네이션 구현
  - ex) http://localhost:8080/wish/all?page=0&size=2&sortBy=price&direction=desc : 0번째 페이지, 페이지 크기는 2, 가격을 기준으로 내림차순 정렬하여 페이지네이션
- [x] product list 페이지네이션 구현
  - ex) http://localhost:8080/product/all?page=0&size=2&sortBy=price&direction=desc : 0번째 페이지, 페이지 크기는 2, 가격을 기준으로 내림차순 정렬하여 페이지네이션

### 2. 테스트 코드 작성
- [x] wish list 페이지네이션 테스트
- [x] product list 페이지네이션 테스트


