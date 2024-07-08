package gift.product.application;

import gift.exception.type.NotFoundException;
import gift.product.application.command.ProductCreateCommand;
import gift.product.application.command.ProductUpdateCommand;
import gift.product.domain.Product;
import gift.product.domain.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream().map(ProductResponse::from).toList();
    }

    public ProductResponse findById(Long productId) {
        return productRepository.findById(productId)
                .map(ProductResponse::from)
                .orElseThrow(() -> new NotFoundException("해당 상품이 존재하지 않습니다."));
    }

    public void add(ProductCreateCommand command) {
        Product product = command.toProduct();
        product.validateKakaoInName();

        productRepository.addProduct(product);
    }

    public void update(ProductUpdateCommand command) {
        Product product = command.toProduct();
        product.validateKakaoInName();

        productRepository.findById(product.getId())
                .orElseThrow(() -> new NotFoundException("해당 상품이 존재하지 않습니다."));

        productRepository.updateProduct(product);
    }

    public void delete(Long productId) {
        productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("해당 상품이 존재하지 않습니다."));

        productRepository.deleteProduct(productId);
    }
}
