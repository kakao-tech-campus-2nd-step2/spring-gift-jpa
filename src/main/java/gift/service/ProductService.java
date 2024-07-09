package gift.service;

import gift.dto.ProductDto;
import gift.entity.Product;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.List;

@ControllerAdvice
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(ProductDto productDto) {
        Product newProduct = new Product(productDto.getName(), productDto.getPrice(), productDto.getUrl());
        return productRepository.save(newProduct);
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getOneById(Long id) {
        return productRepository.findOneById(id);
    }

    public void update(Long id, ProductDto productDto) {
        Product newProduct = new Product(productDto.getName(), productDto.getPrice(), productDto.getUrl());
        productRepository.update(id, newProduct);
    }

    public void delete(Long id) {
        productRepository.delete(id);
    }

}
