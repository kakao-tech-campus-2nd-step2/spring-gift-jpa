package gift.product.service;

import gift.product.dao.ProductDao;
import gift.product.model.Product;
import gift.product.validation.ProductValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ProductService {
    private final ProductDao productDao;
    private final ProductValidation productValidation;

    @Autowired
    public ProductService(ProductDao productDao, ProductValidation productValidation) {
        this.productDao = productDao;
        this.productValidation = productValidation;
    }

    public ResponseEntity<String> registerProduct(Product product) {
        System.out.println(product.getId()+" "+product.getName()+" "+ product.getPrice()+" "+product.getImageUrl());
        productValidation.registerValidation(product);
        productDao.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product registered successfully");
    }

    public ResponseEntity<String> updateProduct(Product product) {
        productValidation.updateValidation(product);
        productDao.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product update successfully");
    }

    public void deleteProduct(Long id) {
        productDao.deleteById(id);
    }

    public Collection<Product> getAllProducts() {
        return productDao.findAll();
    }

    public Product getProductById(Long id) {
        return productDao.findById(id).orElse(null);
    }

    public Collection<Product> searchProducts(String keyword) {
        return productDao.findByName(keyword);
    }

    public boolean existsById(Long id) {
        return productDao.existsById(id);
    }
}
