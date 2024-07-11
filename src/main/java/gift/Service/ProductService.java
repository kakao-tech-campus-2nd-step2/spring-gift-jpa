package gift.Service;

import gift.Entity.Products;
import gift.Model.Product;
import gift.Repository.ProductJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Qualifier("productService")
public class ProductService {

    private final ProductJpaRepository productJpaRepository;

    @Autowired
    public ProductService(ProductJpaRepository productJpaRepository) {
        this.productJpaRepository = productJpaRepository;
    }

    public List<Products> getAllProducts() {
        return productJpaRepository.findByisDeletedFalse();
    }

    public Optional<Products> getProductById(Long id) {
        return productJpaRepository.findById(id);
    }

    public void saveProduct(Product product) {
        Products products = Products.createProducts(product);
        productJpaRepository.save(products);

    }

    public void updateProduct(Product productDetails) {
        Products products = Products.createProducts(productDetails);
        productJpaRepository.save(products);
    }

    public void deleteProduct(Long id) {
        // 제품 ID로 제품을 찾습니다.
        Optional<Products> productOptional = productJpaRepository.findById(id);
        if (productOptional.isPresent()) {
            // 제품이 존재하면, isDeleted를 true로 설정합니다.
            Products products = productOptional.get();
            products.setDeleted(true);
            // 변경된 상태를 저장합니다.
            productJpaRepository.save(products);
        }

    }

}
