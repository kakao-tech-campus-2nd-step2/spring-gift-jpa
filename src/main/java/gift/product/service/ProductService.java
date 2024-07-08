package gift.product.service;

import gift.product.error.AlreadyExistsException;
import gift.product.model.Product;
import gift.product.repository.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //전체 상품 조회 기능
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    //단일 상품 조회 기능
    public Product getProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> searchProduct(String name) {
        return productRepository.findByCond(name);
    }

    //상품 추가 기능
    public void addProduct(Product product) {
        checkAlreadyExists(product);
        productRepository.save(product);
    }

    //상품 수정 기능
    public void updateProduct(Long id, Product product) {
        checkAlreadyExists(product);
        productRepository.update(id, product);
    }

    //상품 삭제 기능
    public void deleteProduct(Long id) {
        productRepository.delete(id);
    }

    private void checkAlreadyExists(Product product) {
        for (Product p : productRepository.findAll()) {
            if (p.isEqual(product)) {
                throw new AlreadyExistsException("해당 상품이 이미 존재 합니다!");
            }
        }
    }

}
