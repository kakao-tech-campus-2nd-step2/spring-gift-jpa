package gift.service;

import gift.dto.ProductResponse;
import gift.exception.InvalidProductNameWithKAKAOException;
import gift.model.MemberRole;
import gift.model.Product;
import gift.dto.ProductRequest;
import gift.repository.ProductOptionRepository;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductOptionRepository optionRepository;

    public ProductService(ProductRepository productRepository, ProductOptionRepository optionRepository) {
        this.productRepository = productRepository;
        this.optionRepository = optionRepository;
    }

    public ProductResponse addProduct(ProductRequest productRequest, MemberRole memberRole) {
        productNameValidation(productRequest, memberRole);
        var product = createProductWithProductRequest(productRequest);
        var savedProduct = productRepository.save(product);
        return getProductResponseFromProduct(savedProduct);
    }

    public void updateProduct(Long id, ProductRequest productRequest) {
        var product = findProductWithId(id);
        updateProductWithProductRequest(product, productRequest);
    }

    public ProductResponse getProduct(Long id) {
        var product = findProductWithId(id);
        return getProductResponseFromProduct(product);
    }

    public List<ProductResponse> getProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::getProductResponseFromProduct)
                .toList();
    }

    public void deleteProduct(Long id) {
        optionRepository.deleteByProductId(id);
        productRepository.deleteById(id);
    }

    private Product findProductWithId(Long id) {
        return productRepository.findById(id);
    }

    private Product createProductWithProductRequest(ProductRequest productRequest) {
        return new Product(productRequest.name(), productRequest.price(), productRequest.imageUrl());
    }

    private void updateProductWithProductRequest(Product product, ProductRequest productRequest) {
        product.updateProductInfo(productRequest.name(), productRequest.price(), productRequest.imageUrl());
        productRepository.update(product);
    }

    private void productNameValidation(ProductRequest productRequest, MemberRole memberRole) {
        if (!productRequest.name().contains("카카오")) return;
        if (memberRole.equals(MemberRole.ADMIN)) return;
        throw new InvalidProductNameWithKAKAOException("카카오가 포함된 문구는 담당 MD와 협의한 경우에만 사용할 수 있습니다.");
    }

    private ProductResponse getProductResponseFromProduct(Product product) {
        return ProductResponse.of(product.getId(), product.getName(), product.getPrice(), product.getImageUrl());
    }
}
