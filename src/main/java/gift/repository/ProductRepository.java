package gift.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import gift.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
	
	Page<Product> findAll(Pageable pageable);
}
