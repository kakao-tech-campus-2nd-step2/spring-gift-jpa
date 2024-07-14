package gift.service;

import gift.DTO.ProductResponse;
import gift.domain.Product;
import gift.DTO.ProductRequest;
import gift.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductResponse> response = products.stream()
                                        .map(p -> new ProductResponse(
                                            p.getId(),
                                            p.getName(),
                                            p.getPrice(),
                                            p.getImageUrl()
                                        ))
                                        .toList();
        return response;
    }

    public List<ProductResponse> getProductsByPage(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Product> productPage = productRepository.findAll(pageable);
        List<ProductResponse> responses = productPage.stream()
                                        .map(p -> new ProductResponse(
                                            p.getId(),
                                            p.getName(),
                                            p.getPrice(),
                                            p.getImageUrl()
                                        ))
                                        .toList();
        return responses;
    }

    public ProductResponse getProductById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        productOptional.orElseThrow(() -> new RuntimeException("No product with id " + id));

        Product product = productOptional.get();
        ProductResponse response = new ProductResponse(
                                            product.getId(),
                                            product.getName(),
                                            product.getPrice(),
                                            product.getImageUrl()
                                        );
        return response;
    }

    public void addProduct(ProductRequest productRequest) {
        productRepository.findByName(productRequest.getName())
                            .ifPresent(p -> {
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
