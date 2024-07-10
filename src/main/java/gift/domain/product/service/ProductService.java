package gift.domain.product.service;

import gift.domain.product.dao.ProductJpaRepository;
import gift.domain.product.dto.ProductDto;
import gift.domain.product.entity.Product;
import gift.exception.InvalidProductInfoException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductJpaRepository productJpaRepository;

    public ProductService(ProductJpaRepository productJpaRepository) {
        this.productJpaRepository = productJpaRepository;
    }

    public Product create(ProductDto productDto) {
        Product product = productDto.toProduct();
        return productJpaRepository.save(product);
    }

    public List<Product> readAll() {
        return productJpaRepository.findAll();
    }

    public Product readById(long productId) {
        return productJpaRepository.findById(productId)
            .orElseThrow(() -> new InvalidProductInfoException("error.invalid.product.id"));
    }

    public Product update(long productId, ProductDto productDto) {
        Product product = productJpaRepository.findById(productId)
            .orElseThrow(() -> new InvalidProductInfoException("error.invalid.product.id"));

        product.updateInfo(productDto.name(), productDto.price(), productDto.imageUrl());
        return productJpaRepository.save(product);
    }

    public void delete(long productId) {
        Product product = productJpaRepository.findById(productId)
            .orElseThrow(() -> new InvalidProductInfoException("error.invalid.product.id"));

        productJpaRepository.delete(product);
    }
}
