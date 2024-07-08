package gift.repository;

import gift.model.Product;
import gift.model.ProductDTO;
import gift.util.ProductUtility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryProductRepository implements ProductRepository {

    private Map<Long, Product> db = new HashMap<>();
    private static Long id = 0L;

    @Override
    public Product save(ProductDTO productDTO) {
        Product product = ProductUtility.productDTOToDAO(new Product(), productDTO);
        product.setId(++id);

        db.put(product.getId(), product);
        return product;
    }

    @Override
    public boolean delete(Long id) {
        Product result = db.remove(id);
        return result != null;
    }

    @Override
    public Product edit(Long id, ProductDTO productDTO) {
        Product result = findById(id);
        if (result == null) {
            return null;
        }
        return ProductUtility.productDTOToDAO(result, productDTO);
    }

    @Override
    public Product findById(Long id) {
        return db.get(id);
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(db.values());
    }

    public void clearDB() {
        db.clear();
    }
}
