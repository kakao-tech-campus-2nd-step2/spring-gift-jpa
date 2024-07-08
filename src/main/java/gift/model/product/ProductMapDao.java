package gift.model.product;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class ProductMapDao implements ProductDao {

    private final Map<Long, Product> database = new ConcurrentHashMap<>();

    @Override
    public void insert(Product product) {
        Product newProduct = Product.create(Long.valueOf(database.size() + 1), product.getName(),
            product.getPrice(), product.getImageUrl());
        database.put(newProduct.getId(), newProduct);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public List<Product> findAll() {
        return List.copyOf(database.values());
    }

    @Override
    public void deleteById(Long id) {
        database.remove(id);
    }

    @Override
    public void update(Product product) {
        database.replace(product.getId(), product);
    }

    @Override
    public List<Product> findPaging(int page, int size) {
        return null;
    }

    @Override
    public Long count() {
        return Long.valueOf(database.size());
    }
}
