package gift.service;

import gift.dto.CreateProduct;
import gift.dto.EditProduct;
import gift.dto.ProductDto;
import gift.entity.Product;
import gift.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

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

    public Product getOneById(long id) {
        return productRepository.findOneById(id);
    }

    public void update(long id, ProductDto productDto) {
        Product newProduct = new Product(productDto.getName(), productDto.getPrice(), productDto.getUrl());
        productRepository.update(id, newProduct);
    }

    public void delete(long id) {
        productRepository.delete(id);
    }

}
