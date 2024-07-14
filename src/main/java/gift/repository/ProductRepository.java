package gift.repository;

import gift.model.product.Product;
import gift.model.product.ProductName;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.validation.annotation.Validated;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Validated
public interface ProductRepository extends JpaRepository<@Valid Product, Long> {
    boolean existsByName(ProductName name);

    @Modifying
    @Transactional
    @Query("update Product p set p.amount = p.amount - :amount where p.id = :id")
    void purchaseProductById(@Param("id") long id, @Param("amount") int amount);

    Page<Product> findAll(Pageable pageable);
}
