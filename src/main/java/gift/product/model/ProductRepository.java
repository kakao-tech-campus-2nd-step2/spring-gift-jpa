package gift.product.model;

import gift.product.model.dto.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // ID와 isActive 조건을 기반으로 제품 찾기
    @Query("SELECT p FROM Product p WHERE p.id = ?1 AND p.isActive = true")
    Optional<Product> findActiveProductById(Long id);

    // isActive가 true인 모든 제품 찾기
    List<Product> findAllByIsActiveTrue();

    // 제품 추가, 삭제는 JpaRepository 기본 제공 메소드 사용 (save, delete)
}
