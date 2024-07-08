# spring-gift-jpa

## step0 구현 기능

- 2주차 코드 클론

## step1 구현 기능

- JDBC템플릿에서 Spring Data JPA로 변경
- DTO에서 Entity로 카테고리명 변경
- Entity에서 스키마 정의로 인한 schema.sql 대체
- loginRpository내 유저를 검증하는 코드 UserRepository로 이동 및 삭제
- 인터셉트내 토큰 만료 예외 처리
- 상품명으로 "카카오"가 존재하는지 검증하는 코드 삭제 및 정규식으로 대체