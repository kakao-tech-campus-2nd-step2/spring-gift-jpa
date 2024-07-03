# spring-gift-wishlist

---

## Step1

**요구 사항**<br>
아래와 같이 http 메세지를 받도록 구현한다

```http request
GET /api/products HTTP/1.1
```

```http request
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


**필요 조건**<br>
상품 데이터 관리
현재는 별도의 데이터베이스가 없으므로 적절한 컬렉션을 이용하여 메모리에 저장한다.
```java
public class ProductController {
    private final Map<Long, Product> productMap = new HashMap<>();
}
```
----
## 구현 기능
- product 조회
- product 추가
- product 수정
- product 삭제




