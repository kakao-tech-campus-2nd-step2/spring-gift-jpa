package gift.service;


import gift.dto.ProductDto;
import gift.entity.Product;
import gift.exception.ProductNotFoundException;
import gift.repository.ProductRepository;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Page<Product> getProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);

    }

    public Product getProductById(Long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> ProductNotFoundException.of(id));
        return product;
    }

    public void addProduct(ProductDto productDto) {
        Product product = new Product(productDto.getName(), productDto.getPrice(), productDto.getImageUrl());
        productRepository.save(product);
    }

    public void updateProduct(Long id, ProductDto productDto) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> ProductNotFoundException.of(id));
        product.edit(productDto.getName(), productDto.getPrice(), productDto.getImageUrl());

    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> ProductNotFoundException.of(id));
        productRepository.delete(product);
    }
}
