# spring-gift-wishlist

이 프로젝트는 선물 상품을 관리하기 위한 간단한 REST API입니다. 상품을 생성(Create), 조회(Read), 수정(Update), 삭제(Delete)할 수 있습니다. API는 HTTP 요청에 JSON 형식의 데이터로 응답합니다.

## 기능

- **상품 조회**: 모든 상품 목록을 조회합니다.
- **단일 상품 조회**: 상품 ID로 단일 상품의 상세 정보를 조회합니다.
- **상품 추가**: 새로운 상품을 추가합니다.
- **상품 수정**: 상품 ID로 기존 상품의 정보를 수정합니다.
- **상품 삭제**: 상품 ID로 상품을 삭제합니다.

## 엔드포인트

### 1. 모든 상품 조회

- **URL**: `/api/products`
- **Method**: `GET`
- **응답 예시**:
    ```json
    {
        "message": "All products retrieved successfully.",
        "products": [
            {
                "id": 8146027,
                "name": "아이스 카페 아메리카노 T",
                "price": 4500,
                "imageUrl": "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"
            }
        ]
    }
    ```

### 2. 단일 상품 조회

- **URL**: `/api/products/{id}`
- **Method**: `GET`
- **응답 예시 (성공 시)**:
    ```json
    {
        "message": "Product retrieved successfully.",
        "product": {
            "id": 8146027,
            "name": "아이스 카페 아메리카노 T",
            "price": 4500,
            "imageUrl": "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"
        }
    }
    ```
- **응답 예시 (실패 시)**:
    ```json
    {
        "message": "Product not found with id: 999"
    }
    ```

### 3. 새로운 상품 추가

- **URL**: `/api/products`
- **Method**: `POST`
- **요청 본문**:
    ```json
    {
        "name": "아이스 카페 아메리카노 T",
        "price": 4500,
        "imageUrl": "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"
    }
    ```
- **응답 예시**:
    ```json
    {
        "message": "Product created successfully.",
        "product": {
            "id": 8146027,
            "name": "아이스 카페 아메리카노 T",
            "price": 4500,
            "imageUrl": "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"
        }
    }
    ```

### 4. 기존 상품 수정

- **URL**: `/api/products/{id}`
- **Method**: `PUT`
- **요청 본문**:
    ```json
    {
        "name": "아이스 카페 아메리카노 T",
        "price": 5000,
        "imageUrl": "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"
    }
    ```
- **응답 예시**:
    ```json
    {
        "message": "Product updated successfully.",
        "product": {
            "id": 8146027,
            "name": "아이스 카페 아메리카노 T",
            "price": 5000,
            "imageUrl": "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"
        }
    }
    ```

### 5. 기존 상품 일부 수정

#### 단일 상품 수정

- **URL**: `/api/products/{id}`
- **Method**: `PATCH`
- **요청 본문**:
    ```json
    {
        "name": "따뜻한 카페 라떼 T",
        "price": 5500
    }
    ```
- **응답 예시**:
    ```json
    {
        "message": "Product patched successfully.",
        "product": {
            "id": 8146027,
            "name": "따뜻한 카페 라떼 T",
            "price": 5500,
            "imageUrl": "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"
        }
    }
    ```

#### 여러 개의 상품 수정

- **URL**: `/api/products`
- **Method**: `PATCH`
- **요청 본문**:
    ```json
    [
        {
            "id": 8146027,
            "name": "따뜻한 카페 라떼 T",
            "price": 5500
        },
        {
            "id": 8146028,
            "price": 6000,
            "imageUrl": "https://example.com/newimage.jpg"
        }
    ]
    ```
- **응답 예시**:
    ```json
    {
        "message": "All products patched successfully.",
        "updatedProducts": [
            {
                "id": 8146027,
                "name": "따뜻한 카페 라떼 T",
                "price": 5500,
                "imageUrl": "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"
            },
            {
                "id": 8146028,
                "name": "아이스 카페 아메리카노 T",
                "price": 6000,
                "imageUrl": "https://example.com/newimage.jpg"
            }
        ]
    }
    ```

### 6. 상품 삭제

- **URL**: `/api/products/{id}`
- **Method**: `DELETE`
- **응답**: `204 No Content`

## 관리자 화면

이 프로젝트에는 제품 관리를 위한 간단한 관리자 화면도 포함되어 있습니다. 관리자는 웹 브라우저를 통해 제품 목록을 조회할 수 있습니다.

### 엔드포인트

- **URL**: `/admin/products`
- **Method**: `GET`
- **설명**: 이 엔드포인트는 관리자가 모든 제품을 조회할 수 있는 웹 페이지를 렌더링합니다.

### 관리자 화면 설정

- **AdminController.java**: 관리자 화면을 처리하는 컨트롤러입니다.
- **products_admin.html**: Thymeleaf 템플릿 파일로, 관리자 화면의 제품 목록을 렌더링합니다.
- **products-management.js**: 관리자 화면의 버튼들의 기능을 구현합니다.
- **styles.css**: 관리자 화면의 스타일을 지정합니다.

### 기능

- **제품 추가**: 새로운 제품을 추가할 수 있습니다.
- **제품 수정**: 기존 제품의 정보를 수정할 수 있습니다.
- **제품 삭제**: 제품을 삭제할 수 있습니다.
- **페이지네이션**: 제품 목록을 페이지 단위로 나누어 조회할 수 있습니다.

## DB 연결 설정

이 프로젝트는 H2 메모리 데이터베이스를 사용하여 데이터 저장을 처리합니다. 애플리케이션이 시작될 때마다 데이터베이스가 초기화되며, 애플리케이션이 종료되면 데이터는 사라집니다.

### application.properties 파일

src/main/resources/application.properties 파일에서 데이터베이스 설정을 추가합니다. `spring.sql.init.mode=always`을 통해 Application을 실행할 때마다 초기화를 진행합니다.

```
spring.application.name=spring-gift
spring.h2.console.enabled=true
spring.datasource.url=jdbc:h2:mem:step3
spring.datasource.username=sa
spring.sql.init.mode=always
spring.sql.init.schema-locations=classpath:schema.sql
```

### schema.sql 파일

src/main/resources/schema.sql 파일에 데이터베이스 테이블을 생성하는 SQL 스크립트를 추가합니다.

```sql
CREATE TABLE IF NOT EXISTS product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price INTEGER NOT NULL,
    imageUrl VARCHAR(255)
);
```

## 회원 관리

회원 등록 및 로그인을 위한 API를 제공합니다.

### 엔드포인트

#### 1. 회원 등록

- **URL**: `/members/register`
- **Method**: `POST`
- **요청 본문**:
    ```json
    {
        "email": "user@example.com",
        "password": "password123",
        "name": "John Doe"
    }
    ```
- **응답 예시 (성공 시)**:
    ```json
    {
        "message": "Member registered successfully",
        "token": "generated_jwt_token"
    }
    ```
- **응답 예시 (실패 시)**:
    ```json
    {
        "message": "Registration failed"
    }
    ```

#### 2. 회원 로그인

- **URL**: `/members/login`
- **Method**: `POST`
- **요청 본문**:
    ```json
    {
        "email": "user@example.com",
        "password": "password123"
    }
    ```
- **응답 예시 (성공 시)**:
    ```json
    {
        "token": "generated_jwt_token"
    }
    ```
- **응답 예시 (실패 시)**:
    ```json
    {
        "message": "Invalid email or password"
    }
    ```
  
### 데이터베이스 초기화

#### schema.sql 파일

`src/main/resources/schema.sql` 파일에 데이터베이스 테이블을 생성하는 SQL 스크립트를 추가합니다.

```sql
CREATE TABLE IF NOT EXISTS members (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);
```

## 위시 리스트

### 1. 위시 리스트에 상품 추가

- **URL**: `/wishes`
- **Method**: `POST`
- **Authorization**: `Bearer {your-jwt-token}`
- **요청 본문**:
    ```json
    {
        "productName": "New Product"
    }
    ```
- **응답 예시**:
    ```json
    {
        "wish": {
            "id": 1,
            "memberId": 1,
            "productName": "New Product"
        }
    }
    ```

### 2. 위시 리스트 조회

- **URL**: `/wishes`
- **Method**: `GET`
- **Authorization**: `Bearer {your-jwt-token}`
- **응답 예시**:
    ```json
    {
        "wishes": [
            {
                "id": 1,
                "memberId": 1,
                "productName": "New Product"
            }
        ]
    }
    ```

### 3. 위시 리스트에서 상품 삭제

- **URL**: `/wishes/{id}`
- **Method**: `DELETE`
- **Authorization**: `Bearer {your-jwt-token}`
- **응답 예시**:
    ```json
    {
        "removed": true
    }
    ```

### 데이터베이스 초기화

#### schema.sql 파일

`src/main/resources/schema.sql` 파일에 데이터베이스 테이블을 생성하는 SQL 스크립트를 추가합니다.

```sql
CREATE TABLE IF NOT EXISTS wishes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    FOREIGN KEY (member_id) REFERENCES members(id)
);
```