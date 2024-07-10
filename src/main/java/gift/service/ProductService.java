package gift.service;

import gift.common.exception.ProductNotFoundException;
import gift.common.exception.UserNotFoundException;
import gift.model.product.Product;
import gift.model.product.ProductListResponse;
import gift.model.product.ProductRequest;
import gift.model.product.ProductResponse;
import gift.repository.ProductRepository;
import gift.repository.WishRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final WishRepository wishRepository;

    public ProductService(ProductRepository productRepository, WishRepository wishRepository) {
        this.productRepository = productRepository;
        this.wishRepository = wishRepository;
    }

    @Transactional
    public ProductResponse register(ProductRequest productRequest) {
        Product product = productRepository.save(productRequest.toEntity());
        return ProductResponse.from(product);
    }

    public ProductResponse findProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        return ProductResponse.from(product);
    }

    public ProductListResponse findAllProduct() {
        List<Product> productList = productRepository.findAll();
        List<ProductResponse> responseList = productList.stream().map(ProductResponse::from)
            .toList();
        ProductListResponse responses = new ProductListResponse(responseList);
        return responses;
    }

    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        Product product = productRepository.findById(id)
            .orElseThrow(ProductNotFoundException::new);
        product.updateProduct(productRequest);
        return ProductResponse.from(product);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException();
        }
        wishRepository.deleteByProductId(productId);
        productRepository.deleteById(productId);
    }
}
