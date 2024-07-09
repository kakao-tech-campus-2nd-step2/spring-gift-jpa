package gift.product.repository;

import gift.product.model.Product;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

public class JpaProductRepository implements ProductRepository {

    private final EntityManager em;

    public JpaProductRepository(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public Product save(Product product) {
        if (product.getId() == null) {
            em.persist(product);
            return product;
        }

        em.merge(product);
        return product;
    }

    public List<Product> findAll() {
        return em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
    }

    public Optional<Product> findById(Long id) {
        Product product = em.find(Product.class, id);

        return Optional.ofNullable(product);
    }

    @Transactional
    public void deleteById(Long id) {
        Product product = em.find(Product.class, id);
        em.remove(product);
    }
}
