package gift.doamin.product.repository;

import gift.doamin.product.entity.Product;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;


public class MemoryProductRepository implements ProductRepository {
    private final Map<Long, Product> products = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong();

    @Override
    public Product insert(Product product) {
        Long id = sequence.getAndIncrement();
        product.setId(id);
        products.put(id, product);
        return product;
    }

    @Override
    public List<Product> findAll() {
        return List.of((Product) products.values());
    }

    @Override
    public Product findById(Long id) {
        return products.get(id);
    }

    @Override
    public Product update(Product product) {
        products.put(product.getId(), product);
        return product;
    }

    @Override
    public void deleteById(Long id) {
        products.remove(id);
    }

    @Override
    public boolean existsById(Long id) {
        return products.containsKey(id);
    }
}
