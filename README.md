# spring-gift-product

## step2 구현한 기능 목록

- 유저 스키마 제작
- 유저 생성 API
- jwt 토큰 생성
- jwt 토큰 검증
- 유저 로그인 API
- 유저 비밀번호 암호화
- 인가 처리
- 로그인 관련 유효성 검사

## 리팩토링 기능 목록

- [x] createdAt, updatedAt 을 위한 baseEntity 제작
- [x] product에 대한 단위테스트
- [x] Role @Enumerated(EnumType.STRING) 붙이기
- [ ] repository 확장성 있게 리팩토링
- [ ] repository 테스트코드 작성
- [ ] 페이징 옵션 enum 생성
- [ ] n+1 문제 해결
- [ ] controller와 service 분리를 위한 중간 계층 제작