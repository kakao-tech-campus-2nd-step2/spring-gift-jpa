package gift.service;

import gift.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

@Repository
@Validated
public interface ProductRepository extends JpaRepository<Product, Long> {

}