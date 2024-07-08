package gift.repository;

import gift.model.Product;
import gift.model.ProductDTO;

import java.util.List;

import gift.util.ProductUtility;
import jakarta.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class JpaProductRepository implements ProductRepository {

    private final EntityManager em;

    public JpaProductRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Product save(ProductDTO form) {
        Product product = ProductUtility.productDTOToDAO(new Product(), form);
        em.persist(product);
        return product;
    }

    @Override
    public boolean delete(Long id) {
        int result = em.createQuery("delete from Product p where p.id= :id")
                .setParameter("id", id)
                .executeUpdate();
        return result > 0;
    }

    @Override
    public Product edit(Long id, ProductDTO form) {
        Product product = findById(id);
        if (product == null) return null;
        product.setName(form.getName());
        product.setPrice(form.getPrice());
        product.setImageUrl(form.getImageUrl());
        em.merge(product);
        return product;
    }

    @Override
    public Product findById(Long id) {
        Product product = em.find(Product.class, id);
        return product;
    }

    @Override
    public List<Product> findAll() {
        return em.createQuery("select p from Product p", Product.class)
                .getResultList();
    }
}
