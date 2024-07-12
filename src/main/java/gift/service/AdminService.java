package gift.service;

import gift.dto.ProductDTO;
import gift.entity.Product;
import gift.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final ProductRepository repository;

    @Autowired
    public AdminService(ProductRepository repository) {
        this.repository = repository;
    }

    public Optional<Product> getProduct(Long productId) {
        return repository.findById(productId);
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public void saveProduct(ProductDTO productDTO) {
    }

    public void deleteProduct(Long productId) {
        repository.deleteById(productId);
    }

    public void updateProduct(Long id, Product product) {

    }
}
