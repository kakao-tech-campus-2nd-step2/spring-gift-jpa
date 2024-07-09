package gift.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findById(int Id);
    List<Product> findAll();
    Product save(Product product);
    void deleteById(int Id);
}