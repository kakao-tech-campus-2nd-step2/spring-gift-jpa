package gift.product.repository;

import gift.product.model.Product;
import jakarta.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ProductRepository {

    private final EntityManager em;

    public ProductRepository(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public Product save(Product product) {
        em.persist(product);
        return product;
    }

    public List<Product> findAll() {
        return em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
    }

    public Product findById(Long id) throws Exception {
        Product product = em.find(Product.class, id);

        if (product == null) {
            throw new Exception();
        }

        return product;
    }

    @Transactional
    public void update(Product product) {
        em.merge(product);
    }

    @Transactional
    public void delete(Long id) {
        Product product = em.find(Product.class, id);
        em.remove(product);
    }
}
