package gift.repository.product;

import gift.domain.Product;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    // id에 따른 상품 조회
    Optional<Product> findById(Long id);

    // 모든 상품 조회
    List<Product> findAll();

    // 상품 추가
    public Product save(Product product);

    // 상품 수정
    public void update(Long id, Product product);

    // 상품 삭제
    public void deleteById(Long id);

    // 상품 삭제 시 목록의 id 재정렬
//    public void orderId();

}
