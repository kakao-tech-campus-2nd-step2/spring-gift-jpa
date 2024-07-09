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

## step2 구현 기능

- 객체의 참조와 테이블의 외래 키를 매핑해서 객체에서는 참조를 사용
- 테이블에서는 외래 키를 사용할 수 있도록 한다.
- wishEntity와 productEntity N:1 매핑
- wishEntity와 UserEntity N:1 매핑
- 기존 wishDTO(wishEntity)에서 user와 product를 id를 받아서 사용하던걸 객체로 받게 수정
- ProductList 조회시 발생한는 순환 참조를 @JsonIgnore로 제한함
- crate, update 일시 추가

## step3 구현 기능
- 페이지 네이션 구현
- get List형태로 반환되는 경우 적용됨
- 기존 ListResult를 상속받아 구현
  - 페이지 사이즈
  - 현재 페이지
  - 페이지 수 
  - 다음 페이지가 존재하는지
  - 총 컨텐츠 수
- body가 아닌 param으로 값을 받음
 