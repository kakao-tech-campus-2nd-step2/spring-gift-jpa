# spring-gift-jpa

## 1단계 구현 기능 목록 명세

---

### 구현 기능
### 1. 기존 Repository 리팩토링
- Jdbc -> Jpa
### 2. 테스트 코드
   1. 단위 테스트
      - UserRepository
      - ItemRepository
      - WishListRepository

   2. 통합 테스트
      - 회원 가입 및 로그인 과정 테스트
      - jwt 토큰 발행 후 인증이 필요한 요청 처리