# 카카오톡 선물하기

# 목차

### [1. 기능 목록](#week-1)

* [Week 1](#week-1)

* [Week 2](#week-2)

* [Week 3](#week-3)

### [2. API 문서](#API-문서)

# Week 1

## Step 1

* 상품 전체 조회 API

* 상품 추가 API

* 상품 수정 API

* 상품 삭제 API

## Step 2

API 호출은 axios 라이브러리 사용

* 상품 단건 조회 API

* 상품 전체 조회 View

    * 상품 전체 조회 API 호출

* 상품 추가 View

    * 상품 추가 Form

    * 상품 추가 API 호출

* 상품 수정 View

    * 상품 수정 Form

        * 기존에 등록된 상품 정보를 가져오기 위한 상품 단건 조회 API 호출

    * 상품 수정(PUT) API 호출

* 상품 삭제 View

    * 상품 삭제 API 호출

## Step 3

* 상품 데이터를 Map이 아닌 H2 database에서 CRUD 작업이 이루어지게끔 리팩토링

# Week 2

## Step 1

* 상품 추가 또는 수정 시 상품 정보 유효성 검사

    * 상품 이름은 공백을 포함하여 최대 15자까지 입력할 수 있다.

    * 특수 문자 가능: ( ), [ ], +, -, &, /, _ (이 외 특수 문자 사용 불가)

    * "카카오"가 포함된 문구는 담당 MD와 협의한 경우에만 사용할 수 있다.

* 유효성 검사에 실패 했을 때의 예외 처리

* 존재하지 않는 상품에 관한 요청 시 예외 처리

## Step 2

* 사용자(MEMBER) 테이블 생성

* 사용자 회원가입 기능

* 사용자 로그인 기능

    * 사용자로부터 이메일과 비밀번호를 받아 해당 사용자가 DB에 저장되어 있는지 체크한다.

        * 성공 시 토큰을 발급하여 응답한다.

## Step 3

* 위시리스트 테이블 생성

* 토큰을 검사해 인증 처리

    * `@LoginMember` 어노테이션이 붙은 곳에서만 토큰 검사 로직 수행

    * 토큰 검사에 통과하지 못하면, 401 에러 반환

* 토큰 검증 성공 시 사용자별 기능

    * 위시 리스트에 등록된 상품 목록을 조회할 수 있다.

    * 위시 리스트에 상품을 추가할 수 있다.

    * 위시 리스트에 담긴 상품을 삭제할 수 있다.

# Week 3

## Step 1

* JdbcTemplate 기반 코드를 Spring Data JPA 기반 코드로 리팩토링

* `@DataJpaTest` 을 사용하여 테스트

## Step 2

* 위시리스트와 회원, 상품에 연관관계 매핑

## Step 3

* 상품 전체 조회에 페이징 적용

* 위시리스트에 존재하는 상품 조회에 페이징 적용

* 위시리스트 테스트 코드 작성

# API 문서

## 회원 API

### 회원가입 API

* request

  ```http
  POST /api/members/register HTTP/1.1
  Content-Type: application/json
  
  {
      "name": "test",
      "password": "test1234",
      "email": "test@example.com"
  }
  ```

### 로그인 API

* request

  ```http
  POST /api/members/login HTTP/1.1
  ```

* response

  ```http
  HTTP/1.1 200 
  Content-Type: application/json
  
  {
      "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyIiwicm9sZSI6Ik1FTUJFUiIsImV4cCI6MTcyMDI1ODA5Mn0.sLcuneHD_WxRpWVFLwLOXnXXHlJYdqbkAPYuqYj7AHY"
  }
  ```

## 상품 API

### 상품 전체 조회 API

* request

  ```http
  GET /api/products HTTP/1.1
  ```

* response

  ```http
  HTTP/1.1 200 
  Content-Type: application/json
  
  [
    {
      "id": 8146027,
      "name": "아이스 카페 아메리카노 T",
      "price": 4500,
      "imageUrl": "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"
    }
  ]
  ```

### 상품 단건 조회 API

* request

  ```http
  GET /api/products/{product_id} HTTP/1.1
  ```

* response

  ```http
  HTTP/1.1 200 
  Content-Type: application/json
  
  {
    "id": 8146027,
    "name": "아이스 카페 아메리카노 T",
    "price": 4500,
    "imageUrl": "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"
  }
  ```

### 상품 추가 API

* request

  ```http
  POST /api/products HTTP/1.1
  Content-Type: application/json
  
  {
    "name": "아이스 카페 아메리카노 T",
    "price": 4500,
    "imageUrl": "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"
  }
  ```

### 상품 수정 API

* request

  ```http
  PUT /api/products/{product_id} HTTP/1.1
  Content-Type: application/json

  {
    "name": "복숭아 아이스티",
    "price": 3500,
    "imageUrl": "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"
  }
  ```

### 상품 삭제 API

* request

  ```http
  DELETE /api/products/{product_id} HTTP/1.1
  ```

## 위시리스트 API

### 위시리스트에 등록된 상품 전체 조회 API

* request

  ```http
  GET /api/members/{member_id}/wishlists/products HTTP/1.1
  Authorization: Bearer <access_token>
  ```

* response

  ```http
  HTTP/1.1 200 
  Content-Type: application/json
  
  [
    {
      "id": 8146027,
      "name": "아이스 카페 아메리카노 T",
      "price": 4500,
      "imageUrl": "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"
    }
  ]
  ```

### 위시리스트에 상품 추가 API

* request

  ```http
  POST /api/members/{member_id}/wishlists/products HTTP/1.1
  Authorization: Bearer <access_token>
  ```

### 위시리스트에 상품 추가 API

* request

  ```http
  DELETE /api/members/{member_id}/wishlists/products/{product_id} HTTP/1.1
  Authorization: Bearer <access_token>
  ```
