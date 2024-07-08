package gift.repository;

import gift.exception.NotFoundElementException;
import gift.model.Product;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductMemoryRepository implements ProductRepository {

    private final Map<Long, Product> products = new HashMap<>();

    private Long sequentialId = 1L;

    public Product save(Product product) {
        var result = createProductWithId(sequentialId, product);
        return saveProduct(result);
    }

    public void update(Product product) {
        products.put(product.getId(), product);
    }

    public Product findById(Long id) {
        productIdValidation(id);
        return products.get(id);
    }

    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    public void deleteById(Long id) {
        productIdValidation(id);
        products.remove(id);
    }

    /**
     * @param id 만약 존재하지 않는 id를 통해 객체에 접근을 시도할 경우 NotFoundProductException 예외를 발생시킨다.
     */
    private void productIdValidation(Long id) {
        if (!products.containsKey(id)) {
            throw new NotFoundElementException(id + "를 가진 상품이 존재하지 않습니다.");
        }
    }

    private Product saveProduct(Product product) {
        products.put(sequentialId++, product);
        return product;
    }

    private Product createProductWithId(Long id, Product product) {
        return new Product(id, product.getName(), product.getPrice(), product.getImageUrl());
    }
}
