package gift.product.application;

import gift.exception.type.NotFoundException;
import gift.product.application.command.ProductCreateCommand;
import gift.product.application.command.ProductUpdateCommand;
import gift.product.domain.Product;
import gift.product.domain.ProductRepository;
import gift.wishlist.domain.WishlistRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    private final WishlistRepository wishlistRepository;

    public ProductService(ProductRepository productRepository, WishlistRepository wishlistRepository) {
        this.productRepository = productRepository;
        this.wishlistRepository = wishlistRepository;
    }

    public Page<ProductResponse> findAll(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(ProductResponse::from);
    }

    public ProductResponse findById(Long productId) {
        return productRepository.findById(productId)
                .map(ProductResponse::from)
                .orElseThrow(() -> new NotFoundException("해당 상품이 존재하지 않습니다."));
    }

    @Transactional
    public void save(ProductCreateCommand command) {
        Product product = command.toProduct();
        product.validateKakaoInName();

        productRepository.save(product);
    }

    @Transactional
    public void update(ProductUpdateCommand command) {
        Product product = productRepository.findById(command.id())
                .orElseThrow(() -> new NotFoundException("해당 상품이 존재하지 않습니다."));

        product.update(command);
        product.validateKakaoInName();
    }

    @Transactional
    public void delete(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("해당 상품이 존재하지 않습니다."));

        wishlistRepository.deleteAllByProductId(productId);
        productRepository.delete(product);
    }
}
