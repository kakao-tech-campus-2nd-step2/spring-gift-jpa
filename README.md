# spring-gift-jpa

## 3단계 구현 기능 목록 명세

---

### 구현 기능
### 1. 엔티티 별 연관관계 매핑
- User <- Wish -> Product
  - wish -> user (N : 1)
  - wish -> product (N : 1)
### 2. 엔티티 변경에 의한 관련 코드 리펙토링