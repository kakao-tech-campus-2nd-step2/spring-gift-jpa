package gift.service;

import gift.dto.request.ProductRequest;
import gift.domain.Product;
import gift.exception.InvalidProductDataException;
import gift.exception.ProductNotFoundException;
import gift.repository.product.ProductSpringDataJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional()
public class ProductService {
    private final ProductSpringDataJpaRepository productRepository;

    @Autowired
    public ProductService(ProductSpringDataJpaRepository productRepository){
        this.productRepository = productRepository;
    }

    public Product register(ProductRequest productRequest){
        Product product = Product.RequestToEntity(productRequest);
        try {
            return productRepository.save(product);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidProductDataException("상품 데이터가 유효하지 않습니다: " + e.getMessage(), e);
        }

    }

    public Page<Product> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product findOne(Long productId){
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("존재하지 않는 상품입니다."));
    }

    public Product update(Long productId, ProductRequest productRequest) {
        return productRepository.findById(productId).map(product -> {
            product.setName(productRequest.getName());
            product.setPrice(productRequest.getPrice());
            product.setImageUrl(productRequest.getImageUrl());
            return productRepository.save(product);
        }).orElseThrow(() -> new ProductNotFoundException("존재하지 않는 상품입니다."));
    }

    public Product delete(Long productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("존재하지 않는 상품입니다."));
        productRepository.delete(product);
        return product;
    }
}
