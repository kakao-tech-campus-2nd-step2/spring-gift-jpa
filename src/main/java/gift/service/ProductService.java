package gift.service;

import gift.dao.ProductDao;
import gift.vo.Product;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> getAllProducts() {
        return productDao.getProducts();
    }

    public Product getProductById(Long id) {
        return productDao.getProductById(id);
    }

    public Boolean addProduct(@Valid Product product) {
        return productDao.addProduct(product);
    }

    public Boolean updateProduct(@Valid Product product) {
        return productDao.updateProduct(product);
    }

    public Boolean deleteProduct(Long id) {
        return productDao.deleteProduct(id);
    }
}
