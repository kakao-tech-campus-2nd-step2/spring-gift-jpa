package gift.service;

import gift.domain.Product;
import gift.repository.ProductDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductDao productDao;

    @Autowired
    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> getAllProducts() {
        return productDao.findAll();
    }

    public Product getProductById(Long id) {
        return productDao.findById(id);
    }

    public void createProduct(Product product) {
        productDao.save(product);
    }

    public void deleteProduct(Long id) {
        productDao.deleteById(id);
    }

    public void updateProduct(Long id, Product productDetails) {
        productDao.update(id, productDetails);
    }
}
