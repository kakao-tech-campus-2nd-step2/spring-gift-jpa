package gift.service;

import gift.model.Product;
import gift.repository.ProductDao;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> getAllProducts() {
        return productDao.findAll();
    }

    public Product getProduct(Long id) {
        return productDao.findProductById(id);
    }

    public void insertProduct(String name, Integer price, String imageUrl) {
        Product product = new Product(name, price, imageUrl);
        productDao.insert(product);
    }

    public void updateProduct(Long id, String name, Integer price, String imageUrl) {
        Product product = new Product(id, name, price, imageUrl);
        productDao.update(product);
    }

    public void deleteProduct(Long id) {
        productDao.delete(id);
    }
}
