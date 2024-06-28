# spring-gift-product

# 1단계 과제

## 기능 요구 사항
상품을 조회, 추가, 수정, 삭제할 수 있는 간단한 HTTP API를 구현한다.

- HTTP 요청과 응답은 JSON 형식으로 주고받는다.
- 현재는 별도의 데이터베이스가 없으므로 적절한 자바 컬렉션 프레임워크를 사용하여 메모리에 저장한다.

## 구현할 기능 목록

- [x] 상품 데이터를 메모리에 저장해야한다.

  - [x] 상품 데이터를 저장하기 위한 Product class를 생성한다.</br>
    - id : primary key 상품 데이터를 찾기 위한 data </br>
    - name : 상품명을 저장하기 위한 data </br>
    - price : 상품의 가격을 저장하기 위한 data </br>
    - description : 상품의 설명을 저장하기 위한 data </br>
    - imageUrl : 상품의 이미지를 저장하기 위한 data </br>
    
  - [x] 상품 데이터를 저장하기 위한 Map<Long, Product> 객체를 생성한다.

- [X] 상품을 추가하는 HTTP API를 구현한다.
  - [X] 상품 추가 시, 상품 id는 자동 생성된다.
  - [X] 상품을 추가하기 위해서는 name, price, description은 필수로 입력해야한다.
  - [X] 상품 추가를 위해, client에게 상품의 정보(name, price, description)을 전달받는다.
  - [X] 상품 추가가 성공하면 200 ok를 전달한다.
  - [X] 상품 정보 중 일부를 미입력해서 상품 추가가 실패한 경우, "상품 정보를 모두 입력해야합니다." 를 전달한다.

- [x] 전체 상품을 조회하는 HTTP API를 구현한다.
  - [x] 상품 조회가 성공적으로 이루어졌다면 상품의 정보(id, name, price, description)를 리스트들로 전달한다.
  - [x] 상품이 존재하지 않아 상품 조회가 실패했다면, "존재하는 상품이 없습니다." 를 전달한다.

- [x] 특정 상품을 조회하는 HTTP API를 구현한다.
  - [x] 상품은 상품 id로 조회한다.
  - [x] 상품 조회를 위해, client에게 상품 id를 전달 받는다.
  - [x] 상품 조회가 성공적으로 이루어졌다면 상품의 정보(name, price, description)를 전달한다.
  - [x] 상품이 존재하지 않아 상품 조회가 실패했다면, " 일치하는 상품이 없습니다" 를 전달한다.

- [x] 상품을 수정하는 HTTP API를 구현한다.
  - [x] 수정할 상품은 상품 id로 조회한다.
  - [x] 상품을 수정할 때, client에서 상품 id, name, price, description을 전달한다.
  - [x] client에서 전달받은 데이터들로 상품 정보들을 수정한다.
  - [x] 수정이 성공적으로 완료되면 200, ok를 보낸다.
  - [x] 수정이 실패한 경우 오류 메시지를 보낸다.

- [x] 상품을 삭제하는 HTTP API를 구현한다.
  - [x] 상품 삭제는 상품 id로 데이터를 조회해서 삭제한다.
  - [x] 상품을 삭제하기 위해, client에게 상품 id를 전달 받는다.
  - [x] 상품 삭제에 성공하면 200 ok를 전달한다.
  - [x] 상품 삭제 중 해당 아이디의 상품이 조회가 안되서 삭제가 안된 경우, "상품이 존재하지 않습니다"를 전달한다.

---

# 2단계 과제

## 기능 요구 사항
상품을 조회, 추가, 수정, 삭제할 수 있는 관리자 화면을 구현한다.

- Thymeleaf를 사용하여 서버 사이드 렌더링을 구현한다.
- 기본적으로는 HTML 폼 전송 등을 이용한 페이지 이동을 기반으로 하지만, 자바스크립트를 이용한 비동기 작업에 관심이 있다면 이미 만든 상품 API를 이용하여 AJAX 등의 방식을 적용할 수 있다.
- 상품 이미지의 경우, 파일을 업로드하지 않고 URL을 직접 입력한다.

## 구현할 기능 목록
- [] 전체 페이지
  - [] 모든 왼쪽 페이지 상단에 main 페이지로 돌아갈 수 있는 버튼이 존재한다.

- [x] Main page (메인 페이지)
  - [x] 전체 상품 리스트를 볼 수 있다.
    - [x] 상품의 이름, 가격만 표시한다.
  - [x] 상품 추가 버튼 클릭 시 상품 추가 페이지로 이동한다.
  - [x] 상품 삭제 버튼 클릭 시 상품 삭제 확인 모달창을 띄운다.
  - [x] 상품 수정 버튼 클릭 시 상품 수정 페이지로 이동한다.
  - [x] 상품 클릭 시 상품 상세 보기 페이지로 이동한다.
  - [x] 리스트 한 줄에 상품명, 가격, 수정버튼, 삭제버튼 있도록 구성한다.
  - [x] 상품 리스트 10개씩 보이게하고, 10개 넘으면 다음 페이지에서 확인할 수 있도록 구성한다.
  - [x] 왼쪽 페이지 상단에 main 페이지로 돌아갈 수 있는 버튼이 존재한다.
- [x] ProductAdd page(상품 추가 페이지)
  - [x] 상품 이름 입력 칸 생성한다.
  - [x] 상품 가격 입력 칸 생성한다.
  - [x] 상품 이미지 입력 칸 생성한다.
  - [x] 상품 설명 입력 칸 생성한다.
  - [x] 상품 등록 버튼 클릭 시, 상품 등록 완료 모달창을 띄우고 상품 상세 페이지로 이동한다.
  - [x] 왼쪽 페이지 상단에 main 페이지로 돌아갈 수 있는 버튼이 존재한다.


-[] ProductView page(상품 상세 정보 확인 페이지)
  - [x] 상품 이름, 상품 가격, 상품 이미지, 상품 설명 표시한다.
  - [] 상품 수정 버튼 클릭시, 상품 수정 페이지로 이동한다.
  - [x] 상품 삭제 버튼 클릭시, 상품 삭제 확인 모달창을 띄운다.
  - [x] 왼쪽 페이지 상단에 main 페이지로 돌아갈 수 있는 버튼이 존재한다.

-[x] ProductUpdate page(상품 정보 업데이트 페이지)
  - [x] 원래의 상품 이름, 상품 가격, 상품 이미지, 상품 설명을 채워놓는다.
  - [x] 상품 이름, 가격, 이미지, 설명을 수정할 수 있도록 한다.
  - [x] 상품 수정 완료 버튼을 누르면, 상품 상세 페이지로 넘어간다.
  - [x] 상품 수정 취소 버튼을 누르면, 이전 페이지로 돌아간다.
  - [x] 왼쪽 페이지 상단에 main 페이지로 돌아갈 수 있는 버튼이 존재한다.

---
# 3단계 과제 : DB 사용하기

## 기능 요구 사항
자바 컬렉션 프레임워크를 사용하여 메모리에 저장하던 상품 정보를 데이터베이스에 저장한다.

## 프로그래밍 요구 사항
메모리에 저장하고 있던 모든 코드를 제거하고 H2 데이터베이스를 사용하도록 변경한다.
사용하는 테이블은 애플리케이션이 실행될 때 구축되어야 한다.

## 구현할 기능 목록 

- [] 데이터베이스 설정
  - [x] H2 데이터베이스 설정
  - [] 필요한 테이블 구조 정의 및 생성

- [] 상품 정보 관리
  - [] 상품 정보 입력/저장
  - [] 상품 정보 조회
  - [] 상품 정보 수정
  - [] 상품 정보 삭제

- [] 상품 정보 데이터베이스 CRUD (Create, Read, Update, Delete) 구현
  - [] 상품 정보 생성 (Create)
  - [] 상품 정보 조회 (Read)
  - [] 상품 정보 수정 (Update)
  - [] 상품 정보 삭제 (Delete)

- [] 기존 메모리 기반 코드 수정
  - [] 메모리에 저장하던 상품 정보 관련 코드 삭제
  - [] 데이터베이스 기반 코드로 전환

- [] 애플리케이션 실행 시 테이블 자동 생성
  - [] 애플리케이션 실행 시 데이터베이스 테이블 자동 생성 기능 구현

- [] 예외 처리 및 오류 처리
  - [] 데이터베이스 연결 실패, 쿼리 실행 실패 등의 예외 처리
  - [] 적절한 오류 메시지 출력

- [] 테스트 및 검증
  - 각 기능별 단위 테스트 수행
  - 통합 테스트를 통한 전체 시스템 검증

## 나중에 시도해볼 것
- [] 성능 및 효율성 고려
  - [] 데이터베이스 연결 및 쿼리 실행 최적화
  - [] 필요한 경우 인덱스 생성 등의 성능 향상 방안 고려
