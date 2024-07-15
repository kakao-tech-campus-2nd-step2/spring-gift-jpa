# spring-gift-jpa

- 상품을 조회, 추가, 수정, 삭제할 수 있는 API를 구현한다.
- H2 database를 연결하여 저장하도록 한다.
	- DAO는 JPA를 사용한다.
	- 이때, Member, Wishlist는 1:N 양방향 매핑.
	- Product, Wishlist도 1:N 양방향 매핑.
- 관리자 웹페이지를 구현한다.
	- Thymeleaf를 사용하여 서버 사이드 렌더링으로 구현한다.
	- 상품 이미지는 파일 업로드가 아닌 URL을 직접 입력한다.
- 상품 정보 입력 값을 검증한다.
	- 상품 이름은 공백 포함 최대 15자
	- 상품 이름은 한글/영어/숫자/특수 문자로 이루어진다.
		- 특수 문자는 (, ), [, ], +, -, &, /, _ 만 사용 가능하다.
	- "카카오"가 포함된 문구는 담당 MD와 협의한 뒤에 사용할 수 있다.	
- 회원가입/로그인 기능을 가진다.
	- 로그인 시, JWT를 발급하여 클라이언트에게 반환한다.
- 위시 리스트 기능을 가진다.
	- 상품 조회 페이지에서 위시 리스트에 추가 버튼을 눌러 추가할 수 있다.
	- 위시 리스트에서 삭제 버튼을 통해 삭제할 수 있다.
- 상품 목록과 위시리스트에 페이지네이션 기능과 정렬 기능을 지원한다.

## 기능 요구사항 명세
### 상품 조회 페이지
- 모든 상품들의 정보를 나타내는 페이지를 서버 사이드 렌더링하여 반환.
```
GET /api/products
```
### 상품 추가 페이지
- 추가할 상품의 정보를 폼 태그에 입력하는 페이지.
```
GET /api/products/prduct
```

### 상품 추가
- HTML 폼 정보에 맞게 상품을 추가하고 목록 페이지로 리다이렉션.
```
POST /api/products/product
```

### 상품 수정 페이지
- 상품의 정보를 수정할 수 있는 폼 태그 페이지.
```
GET /api/products/product/{id}
```


### 상품 수정
- 수정된 정보에 맞게 상품을 수정하고 목록 페이지로 리다이렉션.
```
POST /api/products/product
```

### 상품 삭제
- 해당하는 상품을 삭제.
```
DELETE /api/products/product/{id}
```
### 회원가입 폼
```
GET /api/members/register
```

### 회원가입
```
POST /api/members/register
```
### 로그인 폼
```
GET /api/members/login
```
### 로그인
```
POST /api/members/login
```
### 위시 리스트 조회
```
GET /api/members/wishlist
```
### 위시 리스트에 상품 추가
```
POST /api/members/wishlist/{id}
```

### 위시 리스트 상품 삭제
```
DELETE /api/members/wishlist/{id}
```

