package gift.service;

import gift.DTO.Product;
import gift.DTO.ProductRequest;
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

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getAllProductsByIds(List<Long> productIds) {
        return productRepository.findByIdIn(productIds);
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public void addProduct(ProductRequest productRequest) {
        productRepository.findByName(productRequest.getName()).ifPresent(p -> {
            throw new RuntimeException("Product name must be unique");
        });

        Product productEntity = new Product(productRequest.getName(),
                                            productRequest.getPrice(),
                                            productRequest.getImageUrl());
        productRepository.save(productEntity);
    }

    public void updateProduct(Long id, ProductRequest updatedProduct) {
        Product product = getProductByIdOrThrow(id);
        product.setName(updatedProduct.getName());
        product.setPrice(updatedProduct.getPrice());
        product.setImageUrl(updatedProduct.getImageUrl());
        productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = getProductByIdOrThrow(id);
        productRepository.delete(product);
    }

    private Product getProductByIdOrThrow(Long id) {
        return productRepository.findById(id).orElseThrow(() ->
            new RuntimeException("Product not found with id: " + id));
    }
}
