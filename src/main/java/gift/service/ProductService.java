package gift.service;

import gift.model.Product;
import gift.repository.ProductRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Long addProduct(Product product){
        productRepository.add(product);
        return product.getId();
    }

    public Product findProductById(Long id) {
        return productRepository.findById(id);
    }

    public void updateProduct(Long id, Product product){
        productRepository.update(id, product);
    }

    public void deleteProduct(Long id) {
        productRepository.delete(id);
    }
}