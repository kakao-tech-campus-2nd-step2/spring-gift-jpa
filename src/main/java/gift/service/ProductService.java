package gift.service;

import gift.domain.Product;
import gift.dto.request.AddProductRequest;
import gift.dto.request.UpdateProductRequest;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static gift.constant.Message.*;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProduct(Long productId) {
        return productRepository.findProductById(productId).orElse(null);
    }

    public List<Product> getAllProducts() {
        System.out.println(productRepository.findAll());
        return productRepository.findAll();
    }

    public String addProduct(AddProductRequest requestProduct) {
        productRepository.save(new Product(requestProduct));
        return ADD_SUCCESS_MSG;
    }

    public String updateProduct(Long productId, UpdateProductRequest product) {

        Product productToUpdate = productRepository.findProductById(productId).get();

        if (product.getName() != null) {
            productToUpdate.setName(product.getName());
        }
        if (product.getPrice() > 0) {
            productToUpdate.setPrice(product.getPrice());
        }
        if (product.getImageUrl() != null) {
            productToUpdate.setImageUrl(product.getImageUrl());
        }
        productRepository.save(productToUpdate);
        return UPDATE_SUCCESS_MSG;
    }

    public String deleteProduct(Long productId) {
        productRepository.delete(productRepository.findProductById(productId).get());
        return DELETE_SUCCESS_MSG;
    }

}
