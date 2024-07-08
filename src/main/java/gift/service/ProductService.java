package gift.service;

import gift.domain.Product;
import gift.repository.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository=productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Long addProduct(Product product){
        return productRepository.addProduct(product);
    }

    public Product getProduct(Long id){
        return productRepository.findById(id);
    }

    public Long updateProduct(Product product){
        return productRepository.updateProduct(product);
    }

    public Long deleteProduct(Long id){
        return productRepository.deleteProduct(id);
    }

}
