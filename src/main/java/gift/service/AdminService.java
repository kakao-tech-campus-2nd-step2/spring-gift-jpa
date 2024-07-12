package gift.service;

import gift.entity.Product;
import gift.repository.JpaProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final JpaProductRepository repository;

    @Autowired
    public AdminService(JpaProductRepository repository) {
        this.repository = repository;
    }

    public void saveProduct(Product product) {
        repository.save(product);
    }

    public Optional<Product> getProduct(long productId) {
        return repository.findById(productId);
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public void deleteProduct(long productId) {
        repository.deleteById(productId);
    }

    public void updateProduct(long id, Product product) {
        repository.updateById(id, product);
    }
}
