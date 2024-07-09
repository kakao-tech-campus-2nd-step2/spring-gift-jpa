package gift.doamin.product.service;

import gift.doamin.product.entity.Product;
import gift.doamin.product.repository.ProductRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product create(Product product) {
        if (product.getName().contains("카카오")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                "'카카오'가 포함된 문구는 담당 MD와 협의한 경우에만 사용할 수 있습니다.");
        }

        return productRepository.insert(product);
    }

    public List<Product> readAll() {
        return productRepository.findAll();
    }

    public Product readOne(Long id) {
        Product product = productRepository.findById(id);

        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return product;
    }

    public Product update(Long userId, Product product, boolean isSeller) {
        Long id = product.getId();

        if (!productRepository.existsById(id)) {
            create(product);
            throw new ResponseStatusException(HttpStatus.CREATED);
        }
        Product target = productRepository.findById(id);

        checkAuthority(userId, target, isSeller);

        product.setUserId(target.getUserId());
        return productRepository.update(product);
    }

    public void delete(Long userId, Long id, boolean isSeller) {
        if (!productRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Product target = productRepository.findById(id);

        checkAuthority(userId, target, isSeller);

        productRepository.deleteById(id);
    }

    private void checkAuthority(Long userId, Product target, boolean isSeller) {
        if (isSeller && !target.getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

    }
}
