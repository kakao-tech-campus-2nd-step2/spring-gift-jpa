package gift.service;

import gift.controller.GlobalMapper;
import gift.controller.product.ProductRequest;
import gift.controller.product.ProductResponse;
import gift.domain.Product;
import gift.exception.ProductAlreadyExistsException;
import gift.exception.ProductNotExistsException;
import gift.repository.ProductRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream().map(GlobalMapper::toProductResponse).toList();
    }

    public ProductResponse find(UUID id) {
        Product target = productRepository.findById(id).orElseThrow(ProductNotExistsException::new);
        return GlobalMapper.toProductResponse(target);
    }

    public ProductResponse save(ProductRequest product) {
        productRepository.findByNameAndPriceAndImageUrl(product.name(), product.price(),
            product.imageUrl()).ifPresent(p -> {
            throw new ProductAlreadyExistsException();
        });
        return GlobalMapper.toProductResponse(productRepository.save(GlobalMapper.toProduct(product)));
    }

    public ProductResponse update(UUID id, ProductRequest product) {
        Product foundProduct = productRepository.findById(id)
            .orElseThrow(ProductNotExistsException::new);
        foundProduct.setName(product.name());
        foundProduct.setPrice(product.price());
        foundProduct.setImageUrl(product.imageUrl());
        return GlobalMapper.toProductResponse(productRepository.save(foundProduct));
    }

    public void delete(UUID id) {
        productRepository.findById(id).orElseThrow(ProductNotExistsException::new);
        productRepository.deleteById(id);
    }
}
