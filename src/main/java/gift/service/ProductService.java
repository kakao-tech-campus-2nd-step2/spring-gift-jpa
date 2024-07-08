package gift.service;

import gift.model.Product;
import gift.model.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * 새 상품을 생성하고 맵에 저장함
     *
     * @param product 저장할 상품 객체
     */
    public Product createProduct(Product product) {
        return productRepository.createProduct(product);
    }

    /**
     * 주어진 ID에 해당하는 상품을 반환함
     *
     * @param id 조회할 상품의 ID
     */
    public Product getProduct(Long id) {
        return productRepository.getProduct(id);
    }

    /**
     * 모든 상품을 반환함
     */
    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    /**
     * 주어진 ID에 해당하는 상품을 삭제함
     *
     * @param id 삭제할 상품의 ID
     */
    public void deleteProduct(Long id) {
        productRepository.deleteProduct(id);
    }

    /**
     * 주어진 상품을 갱신함
     *
     * @param product 갱신할 상품 객체
     */
    public Product updateProduct(Long id, Product product) {
        return productRepository.updateProduct(id, product);
    }

    /**
     * 주어진 ID에 해당하는 상품이 존재하는지 확인함
     *
     * @param id 확인할 상품의 ID
     * @return 상품이 존재하면 true, 그렇지 않으면 false
     */
    public boolean exists(Long id) {
        return productRepository.exists(id);
    }

    /**
     * 상품이 존재하지 않을 때 예외를 발생시킴
     *
     * @param id 확인할 상품의 ID
     */
    public void productNotFoundDetector(Long id) {
        productRepository.productNotFoundDetector(id);
    }

}
