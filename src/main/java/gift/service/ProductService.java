package gift.service;

import gift.dto.request.ProductRequest;
import gift.domain.Product;
import gift.exception.InvalidProductDataException;
import gift.exception.ProductNotFoundException;
import gift.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional()
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository){
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

    public List<Product> findProducts(){
        return productRepository.findAll();
    }

    public Product findOne(Long productId){
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("존재하지 않는 상품입니다."));
    }

    public Product update(Long productId, ProductRequest productRequest) {
        try {
            return productRepository.updateById(productId, productRequest)
                    .orElseThrow(() -> new ProductNotFoundException("존재하지 않는 상품입니다."));
        } catch (DataIntegrityViolationException e) {
            throw new InvalidProductDataException("상품 데이터가 유효하지 않습니다: " + e.getMessage(), e);
        }
    }

    public Product delete(Long productId){
        return productRepository.deleteById(productId)
                .orElseThrow(() -> new ProductNotFoundException("존재하지 않는 상품입니다."));
    }
}
