package gift.main.service;

import gift.main.Exception.CustomException;
import gift.main.Exception.ErrorCode;
import gift.main.dto.ProductRequest;
import gift.main.entity.Product;
import gift.main.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        List<Product> productList = productRepository.findAll();
        return productList;
    }

    @Transactional
    public void addProduct(ProductRequest productRequest) {
        Product product = new Product(productRequest);
        productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }

    @Transactional
    public void updateProduct(long id,ProductRequest productRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
        product.setName(product.getName());
        product.setPrice(product.getPrice());
        product.setImageUrl(product.getImageUrl());

        productRepository.save(product);
    }


    public Product getProduct(long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        return product;
    }





}

