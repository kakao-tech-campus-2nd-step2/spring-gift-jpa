package gift.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gift.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}