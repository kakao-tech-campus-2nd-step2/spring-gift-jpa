package gift.repository.product;

import gift.domain.Product;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class ProductMemoryRepository implements ProductRepository{

    private final Map<Long, Product> products = new HashMap<>();
    private Long Id = 1L;

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(products.get(id));
    }

    @Override
    public List<Product> findAll() {
//        return products.values();
        return new ArrayList<>(products.values());
    }

    @Override
    public Product save(Product product) {
        if (product.getId() == null) {
            product.setId(Id++);
        }
        products.put(product.getId(), product);
        return product;
    }

    @Override
    public void update(Long id, Product product){
        products.put(product.getId(), product);

    }
    @Override
    public void deleteById(Long id) {
        products.remove(id);

    }

//    @Override
//    public void orderId() {
//
//    }
}


