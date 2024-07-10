package gift.product.infrastructure.persistence;

import gift.core.domain.product.Product;
import gift.core.domain.product.ProductRepository;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    private final JpaProductRepository jpaProductRepository;

    @Autowired
    public ProductRepositoryImpl(JpaProductRepository jpaProductRepository) {
        this.jpaProductRepository = jpaProductRepository;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return jpaProductRepository
                .findById(id)
                .map(this::mapToProduct);
    }

    @Override
    public boolean exists(Long id) {
        return jpaProductRepository.existsById(id);
    }

    @Override
    public void save(@Nonnull Product product) {
        jpaProductRepository.save(mapToProductEntity(product));
    }

    @Override
    public long size() {
        return jpaProductRepository.count();
    }

    @Override
    public List<Product> findAll() {
        return jpaProductRepository.findAll().stream().map(this::mapToProduct).toList();
    }

    @Override
    public void remove(Long id) {
        jpaProductRepository.deleteById(id);
    }

    private Product mapToProduct(ProductEntity entity) {
        return new Product(
                entity.getId(),
                entity.getName(),
                entity.getPrice(),
                entity.getImageUrl()
        );
    }

    private ProductEntity mapToProductEntity(Product product) {
        return new ProductEntity(product.id(), product.name(), product.price(), product.imageUrl());
    }
}
