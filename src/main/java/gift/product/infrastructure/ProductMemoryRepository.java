package gift.product.infrastructure;

import gift.product.domain.Product;
import gift.product.domain.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class ProductMemoryRepository implements ProductRepository {
    private final List<Product> products = new ArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public List<Product> findAll() {
        return products;
    }

    public Optional<Product> findById(Long productId) {
        return products.stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst();
    }

    public void addProduct(Product product) {
        products.add(
                new Product(
                        nextId.getAndIncrement(),
                        product.getName(),
                        product.getPrice(),
                        product.getImageUrl()
                )
        );
    }

    public void deleteProduct(Long productId) {
        products.removeIf(product -> product.getId().equals(productId));
    }

    @Override
    public void updateProduct(Product product) {
        products.stream()
                .filter(p -> p.getId().equals(product.getId()))
                .findFirst()
                .ifPresent(p -> {
                    p.update(
                            product.getName(),
                            product.getPrice(),
                            product.getImageUrl()
                    );
                });
    }
}
