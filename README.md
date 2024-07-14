# spring-gift-jpa
이 프로젝트는 상품을 선물할 수 있다.    
👤 관리자는 상품들을 등록, 수정, 삭제 가능       
👥 일반 사용자들은 상품을 위시리스트에 추가, 삭제할 수 있다.

## 🚀 Step2 - 연관관계 매핑

---
- [X] Member : Wish = 1 : N 연관관계 매핑 
- [X] Product : Wish = 1 : N 연관관계 매핑



## 🚀 Step1 - 엔티티 매핑

---



   
**💻JdbcTEmplate 기반 코드를 JPA로 Refactoring**   
- JPA로 Refactoring
    - [X] Member
    - [X] Product
    - [X] Wish
- @DataJpaTest 사용하여 테스트
  - [X] Member Test
  - [X] Product Test
  - [X] Wish Test
