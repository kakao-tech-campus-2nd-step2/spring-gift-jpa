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
    private final ProductValidation productValidation;

    public ProductService(ProductRepository productRepository, ProductValidation productValidation) {
        this.productRepository = productRepository;
        this.productValidation = productValidation;
    }
    private final AtomicLong id = new AtomicLong(1);

    public Product createProduct(CreateProductDto productDto) {
        Product product = new Product(id.getAndIncrement(), productDto.getName(), productDto.getDescription(), productDto.getPrice(), productDto.getImageUrl());
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
        product.setDescription(productDto.getDescription());
        product.setImageUrl(productDto.getImageUrl());
        productRepository.update(product);
        return product;
    }


    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

}
