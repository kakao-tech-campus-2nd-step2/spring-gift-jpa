package gift.service;

import gift.model.Product;
import gift.repository.ProductRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public enum ProductServiceStatus {
        SUCCESS,
        NOT_FOUND,
        ERROR
    }


    public List<Product> getAllProducts() {
        return productRepository.getAllProducts(); // 모든 상품을 조회
    }

    // Read(단일 상품) - getProduct()
    public Product getProduct(Long id) {
        return productRepository.getProduct(id);
    }

    // Create(생성) - addProduct()
    public ProductServiceStatus createProduct(Product product) {
        productRepository.addProduct(product);
        return ProductServiceStatus.SUCCESS;
    }

    // Update(수정) - updateProduct()
    public ProductServiceStatus editProduct(Long id, Product product) {
        Product existingProduct = productRepository.getProduct(id);
        if (existingProduct != null) {
            productRepository.updateProduct(id, product);
            return ProductServiceStatus.SUCCESS;
        }
        return ProductServiceStatus.NOT_FOUND;
    }

    public ProductServiceStatus deleteProduct(Long id) {
        try {
            if (productRepository.getProduct(id) != null) {
                productRepository.removeProduct(id);
                return ProductServiceStatus.SUCCESS; // 성공적으로 삭제되었음을 나타내는 메시지
            }
            return ProductServiceStatus.NOT_FOUND;
        } catch (Exception e) {
            return ProductServiceStatus.ERROR; // 에러 발생 시 메시지
        }
    }

}
