# spring-gift-jpa
## step1
* 모든 JDBC 관련 코드들 JPA로 변경
* @DataJpaTest로 학습 테스트
## step2
* 리팩토링 후 비웠던 service 채우기
* controller도 리팩토링
## step3
* Controller
  * page 정보 받을 수 있도록 파라미터 변경하기
* Service
  * Controller로부터 받은 dto로 Pageable을 만들고 Repository에 넘겨주기
  * Repository로부터 Page를 받아서 Controller에 List로 넘겨주기
* Repository
  * Service로부터 Pageable을 받아서 Page를 만들고 반환하는 메서드를 선언하기
