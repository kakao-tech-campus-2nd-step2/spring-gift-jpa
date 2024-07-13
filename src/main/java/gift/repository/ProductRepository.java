package gift.repository;

import gift.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    /*
     * DB에 저장된 모든 Product 정보를 가져와 반환
     */
    Page<Product> findAll(Pageable pageable);
    /*
     * DB에 저장된 Product 중, ID를 기준으로 하나를 선택하여 반환
     */
    Optional<Product> findById(Long id);
    /*
     * DB에 저장된 Product 중, Name을 기준으로 하나를 선택하여 반환
     */
    Optional<Product> findByName(String name);
    /*
     * DB에 Product 정보를 받아 저장
     */
    Product save(Product product);
    /*
     * id로 DB에 해당 Product가 존재하는지 확인
     */
    boolean existsById(Long id);
    /*
     * DB에 있는 Product를 ID를 기준으로 찾아 삭제
     */
    void deleteById(Long id);
}
