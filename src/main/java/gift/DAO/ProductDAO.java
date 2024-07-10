package gift.DAO;

import gift.Entity.ProductEntity;
import gift.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductDAO {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductEntity> findAll() {
        return productRepository.findAll();
    }

    public Optional<ProductEntity> findById(Long id) {
        return productRepository.findById(id);
    }

    public ProductEntity save(ProductEntity productEntity) {
        return productRepository.save(productEntity);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
