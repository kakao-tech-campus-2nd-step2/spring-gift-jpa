# spring-gift-jpa

## step0 기본 코드 준비

<hr>

## step1 엔티티 매핑

### 1. Entity로 변환
- Product
- Member
- Wishlist

### 2. Repository 재구축
- ProductRepository
- MemberRepository
- WishRepository

### 3. Test 작성

- ProductRepositoryTest
- MemberControllerTest
- WishControllerTest

<hr>

## step2 연관 관계 매핑

### 1. 연관관계 매핑

- Wish 와 Member : ManyToOne
- Wish 와 Product : ManyToOne

### 2. Test 추가
- already existing wish Test

