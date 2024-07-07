# [구현할 기능 목록] 3단계 - 위시 리스트
#### 이전 단계에서 로그인 후 받은 토큰을 사용하여 사용자별 위시 리스트 기능을 구현한다.
#### 단, 사용자 정보는 요청 헤더의 Authorization 필드를 사용한다.
#### ex) Authorization: Bearer token
### 1. HandlerMethodArgumentResolver 구현
#### (1) `LoginMember` 어노테이션 생성 
- [x] 컨트롤러 메서드의 파라미터에 붙여서 로그인한 사용자의 정보를 주입하기 위해 사용됨
#### (2) `JwtUtil` 구현
- [x] 토큰 유효성 검증
- [x] 토큰에서 이메일 정보 추출
- [x] JWT 토큰을 파싱하여 그 안에 포함된 클레임을 반환
#### (3) `LoginMemberArgumentResolver` 구현
- [x] `supportsParameter` : `LoginMember` 어노테이션을 가진 파라미터를 지원하는지 판단
- [x] `resolveArgument` : 토큰값 인증 후 사용자 정보 반환
- [x] `WebConfig`에 `LoginMemberArgumentResolver` 추가
### 2. 위시 리스트 CRUD 기능 구현
#### (1) `WishList` 모델 설계 및 테이블 설정 
- [x] 상품 정보를 담을 `Product` 모델 설계
- [x] 멤버id와 상품들을 담을 `WishList` 모델 설계
- [x] `WishList` 테이블 생성
#### (2) `WishListController` 구현
- [x] 위시 리스트 추가 기능 구현
- [x] 위시 리스트 조회 기능 구현
- [x] 위시 리스트 수정 기능 구현
- [x] 위시 리스트 삭제 기능 구현
#### (3) `WishListService` 구현
#### (4) `WishListRepository` 구현



