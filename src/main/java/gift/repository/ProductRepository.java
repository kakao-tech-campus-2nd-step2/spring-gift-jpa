package gift.repository;

import gift.model.product.Product;
import gift.model.product.ProductName;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(ProductName name);

    @Modifying
    @Transactional
    @Query("update Products p set p.amount = p.amount - :amount where p.id = :id")
    void purchaseProductById(long id, int amount);
}
