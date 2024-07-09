package gift.service;

import gift.exception.ProductException;
import gift.model.Product;
import gift.repository.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new ProductException("상품이 존재하지 않습니다."));
    }

    public void addProduct(String name, Integer price, String imageUrl) {
        Product product = new Product(name, price, imageUrl);
        productRepository.save(product);
    }

    public void editProduct(Long id, String name, Integer price, String imageUrl) {
        productRepository.findById(id)
            .ifPresentOrElse( p -> p.updateProduct(name, price, imageUrl) ,
                () -> {
                    throw new ProductException("상품이 존재하지 않습니다.");
                }
            );
    }

    public void removeProduct(Long id) {
        productRepository.findById(id)
            .ifPresentOrElse(productRepository::delete
                , () -> {
                    throw new ProductException("상품이 존재하지 않습니다.");
                }
            );
    }
}
