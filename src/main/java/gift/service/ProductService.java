package gift.service;

import gift.dto.ProductRequest;
import gift.entity.Product;
import gift.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Product findById(long id) {
        return productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product with id " + id + " not found"));
    }

    public void save(ProductRequest productRequest) {
        Product product = new Product(0, productRequest.name(), productRequest.price(), productRequest.imageUrl());
        productRepository.save(product);
    }

    public void update(Long id, ProductRequest productRequest) {
        Product product = new Product(id, productRequest.name(), productRequest.price(), productRequest.imageUrl());
        productRepository.update(product);
    }

    public void delete(Long id) {
        productRepository.delete(id);
    }

    public void deleteBatch(List<Long> ids) {
        productRepository.deleteBatch(ids);
    }
}
