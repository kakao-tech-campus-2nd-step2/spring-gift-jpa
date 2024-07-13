package gift.service;

import gift.domain.Product;
import gift.dto.CreateProductDto;
import gift.dto.UpdateProductDto;
import gift.repository.ProductRepository;
import gift.validation.ProductValidation;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(CreateProductDto productDto) {
        Product product = new Product(productDto.getName(), productDto.getPrice(), productDto.getImageUrl());
        productRepository.save(product);
        return product;
    }


    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(Long productId) {
        Product product = productRepository.findById(productId);
        return product;
    }

    public Product updateProduct(Long productId, UpdateProductDto productDto) {
        Product product = productRepository.findById(productId);
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());
        productRepository.update(product);
        return product;
    }


    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

}
