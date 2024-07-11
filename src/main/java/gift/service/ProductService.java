package gift.service;

import gift.entity.Product;
import gift.exception.BusinessException;
import gift.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        if (productRepository.findById(id).isEmpty()) {
            throw new BusinessException("해당 아이디에 대한 상품이 존재하지 않습니다.");
        }
        return productRepository.findById(id);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product update(Long id, Product product) {
        return productRepository.findById(id)
            .map(existingProduct -> {
                existingProduct.setName(product.getName());
                existingProduct.setPrice(product.getPrice());
                existingProduct.setImageUrl(product.getImageUrl());
                return productRepository.save(existingProduct);
            }).orElseThrow(() -> new BusinessException("해당 아이디에 대한 상품이 존재하지 않습니다."));
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
