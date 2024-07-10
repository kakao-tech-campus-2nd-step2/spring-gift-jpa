package gift.service;

import gift.dto.ProductRequest;
import gift.dto.ProductResponse;
import gift.exception.InvalidProductNameWithKAKAOException;
import gift.model.MemberRole;
import gift.model.Product;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse addProduct(ProductRequest productRequest, MemberRole memberRole) {
        productNameValidation(productRequest, memberRole);
        var product = saveProductWithProductRequest(productRequest);
        return getProductResponseFromProduct(product);
    }

    public void updateProduct(Long id, ProductRequest productRequest) {
        var product = productRepository.findByIdOrThrow(id);
        updateProductWithProductRequest(product, productRequest);
    }

    public ProductResponse getProduct(Long id) {
        var product = productRepository.findByIdOrThrow(id);
        return getProductResponseFromProduct(product);
    }

    public List<ProductResponse> getProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::getProductResponseFromProduct)
                .toList();
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    private Product saveProductWithProductRequest(ProductRequest productRequest) {
        var product = new Product(productRequest.name(), productRequest.price(), productRequest.imageUrl());
        var savedProduct = productRepository.save(product);
        return savedProduct;
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
}
