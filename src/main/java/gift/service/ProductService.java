package gift.service;

import gift.domain.Product;
import gift.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static gift.util.ProductNameValidationUtil.isValidProductName;
import static gift.constant.Message.*;

@Service
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProduct(Long productId) {
        return productRepository.selectOneProduct(productId);
    }

    public List<Product> getAllProducts() {
        return productRepository.selectAllProducts();
    }

    public String addProduct(Product newProduct) {
        if (isValidProductName(newProduct.getName())) {
            productRepository.insertProduct(newProduct);
        }
        return ADD_SUCCESS_MSG;
    }

    public String updateProduct(Long productId, Product product) {

        Product productToUpdate = productRepository.selectOneProduct(productId);

        if ((product.getName() != null) & (isValidProductName(product.getName()))) {
            productToUpdate.setName(product.getName());
        }
        if (product.getPrice() > 0) {
            productToUpdate.setPrice(product.getPrice());
        }
        if (product.getImageUrl() != null) {
            productToUpdate.setImageUrl(product.getImageUrl());
        }
        productRepository.updateProduct(productToUpdate);
        return UPDATE_SUCCESS_MSG;
    }

    public String deleteProduct(Long productId) {
        productRepository.deleteProduct(productId);
        return DELETE_SUCCESS_MSG;
    }

}
