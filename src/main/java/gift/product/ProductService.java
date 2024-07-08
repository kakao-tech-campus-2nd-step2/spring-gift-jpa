package gift.product;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductDao productDao;

    @Autowired
    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductDTO> getAllProducts() {
        return productDao.findAll().stream()
            .map(ProductDTO::fromProduct)
            .collect(Collectors.toList());
    }

    public ProductDTO getProductById(Long id) {
        return ProductDTO.fromProduct(productDao.selectProduct(id));
    }

    public void addProduct(ProductDTO product) {
        productDao.insertProduct(product.toProduct());
    }

    public void updateProduct(ProductDTO product) {
        productDao.updateProduct(product.toProduct());
    }

    public void deleteProduct(Long id) {
        productDao.deleteProduct(id);
    }
}
