package gift.service;

import gift.dao.ProductDao;
import gift.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }

    public Product getProductById(Long id) {
        return productDao.getProductById(id);
    }

    public void addProduct(Product product) {
        productDao.addProduct(product);
    }

    public void updateProduct(Long id, Product product) {
        productDao.updateProduct(id, product);
    }

    public void deleteProduct(Long id) {
        productDao.deleteProduct(id);
    }
}
