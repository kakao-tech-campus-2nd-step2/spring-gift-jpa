package gift.Service;

import gift.Entity.ProductEntity;
import gift.Entity.WishEntity;
import gift.Repository.ProductRepository;
import gift.Repository.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
 
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WishRepository wishRepository;

    public List<ProductEntity> findAllProducts() {
        return productRepository.findAll();
    }

    public Optional<ProductEntity> findProductById(Long id) {
        return productRepository.findById(id);
    }

    public ProductEntity saveProduct(ProductEntity productEntity) {
        return productRepository.save(productEntity);
    }

    public void deleteProduct(Long id) {
        // 관련된 WishEntity들도 삭제
        List<WishEntity> wishes = wishRepository.findByProductId(id);
        wishRepository.deleteAll(wishes);

        productRepository.deleteById(id);
    }
}
