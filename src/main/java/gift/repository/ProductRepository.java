package gift.repository;

import gift.model.Member;
import gift.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
  List<Product> findAll();
  Optional<Product> findById(Long id);
}

