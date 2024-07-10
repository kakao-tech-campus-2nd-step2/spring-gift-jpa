package gift.doamin.product.service;

import gift.doamin.product.dto.ProductParam;
import gift.doamin.product.entity.Product;
import gift.doamin.product.exception.NotEnoughAutorityException;
import gift.doamin.product.exception.ProductNotFoundException;
import gift.doamin.product.repository.JpaProductRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final JpaProductRepository productRepository;

    public ProductService(JpaProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product create(ProductParam productParam) {
        if (productParam.getName().contains("카카오")) {
            throw new NotEnoughAutorityException("'카카오'가 포함된 문구는 담당 MD와 협의한 경우에만 사용할 수 있습니다.");
        }

        return productRepository.save(productParam.toProduct());
    }

    public List<Product> readAll() {
        return productRepository.findAll();
    }

    public Product readOne(Long id) {
        return productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
    }

    public Product update(Long userId, ProductParam productParam, boolean isSeller) {
        Long id = productParam.getId();

        Optional<Product> target = productRepository.findById(id);
        if (target.isEmpty()) {
            return create(productParam);
        }

        checkAuthority(userId, target.get(), isSeller);

        productParam.setUserId(target.get().getUserId());
        return productRepository.save(productParam.toProduct());
    }

    public void delete(Long userId, Long id, boolean isSeller) {
        Product target = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);

        checkAuthority(userId, target, isSeller);

        productRepository.deleteById(id);
    }

    private void checkAuthority(Long userId, Product target, boolean isSeller) {
        if (isSeller && !target.getUserId().equals(userId)) {
            throw new NotEnoughAutorityException();
        }

    }
}
