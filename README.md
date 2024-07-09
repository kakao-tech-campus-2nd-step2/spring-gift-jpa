# spring-gift-wishlist
인증을 진행해야한다.
회원가입
로그인 

아이디는 이메일, 비밀번호를 입력해야한다.

Member dto 생성
db에 추가
repository, Service, Controller 구현

step3
member과 menu들의 관계를 나타내는 wishList 테이블 구현
wishlistController에서 토큰 유효성 검증, 해당 사용자의 메뉴들 리턴하기
-> 헤더에 있는 토큰 받고, 해당 리스트는 바디에 넣기

wishList에 추가하기 -> 토큰 인증 후 실행
wishList 조회하기
wishList 삭제하기
