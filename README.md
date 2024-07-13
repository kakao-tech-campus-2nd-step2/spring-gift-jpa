원래 JdbcTemplate을 사용하여 데이터베이스와 상호작용하던 코드를 JPA(Java Persistence API)로 리팩터링하는 작업을 진행하였습니다. 

1. 엔티티 클래스와 리포지토리 클래스를 주어진 형식에 맞게 수정
2. @DataJpaTest를 사용하여 학습 테스트 구현

객체 클래스, 컨트롤러 클래스, 서비스 클래스, 리포지토리 클래스를 jpa를 활용해 수정하였습니다.

상품과 위시 리스트 보기에 페이지네이션을 구현하였습니다.

1. 대부분의 게시판은 모든 게시글을 한 번에 표시하지 않고 여러 페이지로 나누어 표시하는 기능. 
2. 정렬 방법을 설정하여 보고 싶은 정보의 우선 순위를 정하는 기능.(해당 attribute 클릭 시 오름차순으로 정렬)

