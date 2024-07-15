# spring-gift-jpa

## Step1 & Step2

### 사용자 관리 JPA 수정

- [ ] 사용자 등록
- [ ] 사용자 조회
- [ ] 사용자 수정
- [ ] 사용자 삭제
- [ ] 사용자 이메일 중복 확인

### 상품 관리 JPA 수정

- [ ] 상품 등록
- [ ] 상품 조회
- [ ] 상품 수정
- [ ] 상품 삭제

### 위시리스트 관리 JPA 수정

- [ ] 위시리스트에 상품 추가
- [ ] 위시리스트 조회
- [ ] 위시리스트 수정
- [ ] 위시리스트 삭제
- [ ] 특정 사용자의 위시리스트 조회

---

## Step3

### 기능 목록

1. **ProductRepository에 페이지네이션 메서드 추가**
   - ProductRepository에 `Page<Product>`를 반환하는 페이지네이션 메서드 추가.

2. **ProductService에 페이지네이션 서비스 메서드 추가**
   - ProductService에 `Pageable` 파라미터를 받는 `getAllProducts` 메서드 추가.
   - 해당 메서드는 `Page<Product>` 타입을 반환하도록 구현.

3. **ProductController에 페이지네이션 엔드포인트 추가**
   - ProductController에 페이지네이션을 지원하는 `getAllProducts` 엔드포인트 추가.
   - `Pageable` 파라미터를 사용하여 제품 목록을 페이지네이션 처리.

4. **WishRepository에 페이지네이션 메서드 추가**
   - WishRepository에 `Page<Wish>`를 반환하는 페이지네이션 메서드 추가.

5. **WishService에 페이지네이션 서비스 메서드 추가**
   - WishService에 `Pageable` 파라미터를 받는 `getWishList` 메서드 추가.
   - 해당 메서드는 `Page<Wish>` 타입을 반환하도록 구현.

6. **WishController에 페이지네이션 엔드포인트 추가**
   - WishController에 페이지네이션을 지원하는 `getWishList` 엔드포인트 추가.
   - `Pageable` 파라미터를 사용하여 위시 리스트를 페이지네이션 처리.

7. **Thymeleaf 뷰 수정**
   - **product.html**:
     - 상품 목록 테이블에 페이지네이션 링크 추가 (첫 페이지, 이전 페이지, 현재 페이지, 다음 페이지, 마지막 페이지).
     - 현재 페이지 번호와 총 페이지 수 표시.
   - **admin/products.html**:
     - 상품 추가, 수정 폼에서 이미지 파일 업로드 필드 추가.
     - 페이지네이션 링크와 현재 페이지 정보 표시.

---

### 커밋 메시지

- ProductRepository에 페이지네이션 메서드 추가
- ProductService에 페이지네이션 서비스 메서드 추가
- ProductController에 페이지네이션 엔드포인트 추가
- WishRepository에 페이지네이션 메서드 추가
- WishService에 페이지네이션 서비스 메서드 추가
- WishController에 페이지네이션 엔드포인트 추가
- Thymeleaf 뷰 수정
