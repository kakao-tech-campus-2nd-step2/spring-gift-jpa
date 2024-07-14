package gift.service;

import gift.dto.ProductRequest;
import gift.dto.ProductResponse;
import gift.exception.InvalidProductNameWithKAKAOException;
import gift.exception.NotFoundElementException;
import gift.model.MemberRole;
import gift.model.Product;
import gift.repository.ProductOptionRepository;
import gift.repository.ProductRepository;
import gift.repository.WishProductRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final WishProductRepository wishProductRepository;
    private final ProductOptionRepository productOptionRepository;

    public ProductService(ProductRepository productRepository, WishProductRepository wishProductRepository, ProductOptionRepository productOptionRepository) {
        this.productRepository = productRepository;
        this.wishProductRepository = wishProductRepository;
        this.productOptionRepository = productOptionRepository;
    }

    public ProductResponse addProduct(ProductRequest productRequest, MemberRole memberRole) {
        productNameValidation(productRequest, memberRole);
        var product = saveProductWithProductRequest(productRequest);
        return getProductResponseFromProduct(product);
    }

    public void updateProduct(Long id, ProductRequest productRequest) {
        var product = findProductById(id);
        updateProductWithProductRequest(product, productRequest);
    }

    @Transactional(readOnly = true)
    public ProductResponse getProduct(Long id) {
        var product = findProductById(id);
        return getProductResponseFromProduct(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .stream()
                .map(this::getProductResponseFromProduct)
                .toList();
    }

    public void deleteProduct(Long productId) {
        wishProductRepository.deleteWishProductsByProductId(productId);
        productOptionRepository.deleteProductOptionsByProductId(productId);
        productRepository.deleteById(productId);
    }

    private Product saveProductWithProductRequest(ProductRequest productRequest) {
        var product = new Product(productRequest.name(), productRequest.price(), productRequest.imageUrl());
        return productRepository.save(product);
    }

    private void updateProductWithProductRequest(Product product, ProductRequest productRequest) {
        product.updateProductInfo(productRequest.name(), productRequest.price(), productRequest.imageUrl());
        productRepository.save(product);
    }

    private void productNameValidation(ProductRequest productRequest, MemberRole memberRole) {
        if (!productRequest.name().contains("카카오")) return;
        if (memberRole.equals(MemberRole.ADMIN)) return;
        throw new InvalidProductNameWithKAKAOException("카카오가 포함된 문구는 담당 MD와 협의한 경우에만 사용할 수 있습니다.");
    }

    private ProductResponse getProductResponseFromProduct(Product product) {
        return ProductResponse.of(product.getId(), product.getName(), product.getPrice(), product.getImageUrl());
    }

    private Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundElementException(id + "를 가진 상품옵션이 존재하지 않습니다."));
    }
}
