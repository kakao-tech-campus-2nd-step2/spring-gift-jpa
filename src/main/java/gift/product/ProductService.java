package gift.product;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> getAllPrdouct() {
        return productDao.findAllProduct();
    }

    public Optional<Product> getProductById(Long id) {
        return productDao.findProductById(id);
    }

    public void postProduct(Product product) {
        productDao.addProduct(product);
    }

    public Integer putProduct(Long id, ProductRequestDto productRequestDto) {
        return productDao.updateProductById(id, productRequestDto);
    }

    public void deleteProductById(Long id) {
        productDao.deleteProductById(id);
    }

}
