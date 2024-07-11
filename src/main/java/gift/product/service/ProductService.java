package gift.product.service;

import gift.product.domain.Product;
import gift.product.exception.ProductNotFoundException;
import gift.product.persistence.ProductRepository;
import gift.product.service.dto.ProductInfo;
import gift.product.service.dto.ProductParams;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Long saveProduct(ProductParams productRequest) {
        Product product = productRequest.toEntity();
        product = productRepository.save(product);

        return product.getId();
    }

    @Transactional
    public void modifyProduct(final Long id, ProductParams productRequest) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> ProductNotFoundException.of(id));

        product.modify(productRequest.name(), productRequest.price(), productRequest.imgUrl());
    }

    @Transactional(readOnly = true)
    public ProductInfo getProductDetails(final Long id) {
        Product foundProduct = productRepository.findById(id)
                .orElseThrow(() -> ProductNotFoundException.of(id));

        return ProductInfo.from(foundProduct);
    }

    @Transactional(readOnly = true)
    public List<ProductInfo> getProducts() {
        List<Product> foundProducts = productRepository.findAll();

        return foundProducts.stream()
                .map(ProductInfo::from)
                .toList();
    }

    @Transactional
    public void deleteProduct(final Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> ProductNotFoundException.of(id));
        productRepository.delete(product);
    }
}
