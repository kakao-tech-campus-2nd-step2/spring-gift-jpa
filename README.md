# spring-gift-product
-ProductController : H2데이터베이스에 데이터를 CRUD
1. init() : product테이블 생성
2. getAllProducts() : 모든상품조회
3. getProductById : id로 상품조회
4. addProduct : 상품추가
5. updateProduct : 삼품업데이트
6. deleteProduct : 상품삭제

-ProductAdminController : 관리자페이지를 위한 controller
1. listProducts : 모든 제품을 조회하고 이를 "product-list" 뷰에 렌더링하기 위해 모델에 추가
2. newProductForm: 새로운 Product 객체를 초기화하고 "product-form" 뷰를 준비하여 새 제품을 추가
3. addProduct: POST 요청을 처리하여 시스템에 새 제품을 추가하고 제품 목록으로 리디렉션
4. editProductForm: ID로 제품을 조회하고 해당 제품이 존재하면 "product-form" 뷰를 편집을 위해 준비
5. updateProduct: POST 요청을 처리하여 ID로 식별된 기존 제품을 업데이트하고 제품 목록으로 리디렉션
6. deleteProduct: POST 요청을 처리하여 ID로 식별된 제품을 삭제하고 제품 목록으로 리디렉션