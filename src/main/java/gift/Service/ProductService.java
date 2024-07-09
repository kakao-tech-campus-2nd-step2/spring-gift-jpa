package gift.Service;

import gift.Model.Product;
import gift.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Qualifier("productService")
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAllProduct();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public void saveProduct(Product product) {
        productRepository.saveProduct(product);
    }

    public void updateProduct(Long id, Product productDetails) {
        productRepository.updateProduct(id, productDetails);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteProductById(id);
    }
}
