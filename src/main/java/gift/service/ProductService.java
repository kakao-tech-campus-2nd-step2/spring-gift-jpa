package gift.service;

import gift.dto.ProductDto;
import gift.entity.Product;
import gift.repository.ProductRepositoryInterface;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.List;

@ControllerAdvice
@Service
@Transactional
public class ProductService {

    private final ProductRepositoryInterface productRepositoryInterface;

    public ProductService(ProductRepositoryInterface productRepositoryInterface) {
        this.productRepositoryInterface = productRepositoryInterface;
    }

    public Product createProduct(String name, Long price, String url) {
        Product newProduct = new Product(name, price, url);
        return productRepositoryInterface.save(newProduct);
    }

    public List<Product> getAll() {
        return productRepositoryInterface.findAll();
    }

    public Product getOneById(Long id) {
        return productRepositoryInterface.findById(id).get();
    }

    public void update(Long id, String name, Long price, String url) {
        Product actualProduct = productRepositoryInterface.findById(id).orElseThrow(() -> new RuntimeException("상품을 찾지 못했습니다."));
        actualProduct.update(name,price,url);
    }

    public void delete(Long id) {
        Product product = productRepositoryInterface.findById(id).get();
        productRepositoryInterface.delete(product);
    }

    public Product findProductByName(String name) {
        return productRepositoryInterface.findByName(name);
    }
}
