package gift.feat.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gift.feat.product.domain.Product;

@Repository
public interface ProductJpaRepository extends JpaRepository<Product, Long> {
	Product findByName(String name);
	Page<Product> findAll(Pageable pageable);
	Page<Product> findByNameContaining(String name, Pageable pageable);
}
