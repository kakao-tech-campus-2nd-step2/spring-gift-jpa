package gift.service;

import gift.dto.ProductOptionRequest;
import gift.dto.ProductOptionResponse;
import gift.dto.ProductResponse;
import gift.exception.NotFoundElementException;
import gift.model.ProductOption;
import gift.repository.ProductOptionRepository;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductOptionService {

    private final ProductOptionRepository productOptionRepository;
    private final ProductRepository productRepository;


    public ProductOptionService(ProductOptionRepository productOptionRepository, ProductRepository productRepository) {
        this.productOptionRepository = productOptionRepository;
        this.productRepository = productRepository;
    }

    public ProductOptionResponse addOption(ProductOptionRequest productOptionRequest) {
        var productOption = saveProductOptionWithProductRequest(productOptionRequest);
        return getProductOptionResponseFromProductOption(productOption);
    }

    public void updateOption(Long id, ProductOptionRequest productOptionRequest) {
        var productOption = findProductOptionById(id);
        productOption.updateOptionInfo(productOptionRequest.name(), productOptionRequest.additionalPrice());
        productOptionRepository.save(productOption);
    }

    public ProductOptionResponse getOption(Long id) {
        var productOption = findProductOptionById(id);
        return getProductOptionResponseFromProductOption(productOption);
    }

    public List<ProductOptionResponse> getOptions(Long productId) {
        return productOptionRepository.findAllByProductId(productId)
                .stream()
                .map(this::getProductOptionResponseFromProductOption)
                .toList();
    }

    public void deleteOption(Long id) {
        var productOption = findProductOptionById(id);
        productOption.removeOption();
        productOptionRepository.deleteById(id);
    }

    private ProductOption saveProductOptionWithProductRequest(ProductOptionRequest productOptionRequest) {
        var product = productRepository.findById(productOptionRequest.productId())
                .orElseThrow(() -> new NotFoundElementException(productOptionRequest.productId() + "를 가진 상품이 존재하지 않습니다."));
        var productOption = new ProductOption(productOptionRequest.name(), productOptionRequest.additionalPrice());
        productOption.addProduct(product);
        return productOptionRepository.save(productOption);
    }

    private ProductOptionResponse getProductOptionResponseFromProductOption(ProductOption productOption) {
        var product = productOption.getProduct();
        var productResponse = ProductResponse.of(product.getId(), product.getName(), product.getPrice(), product.getImageUrl());
        return ProductOptionResponse.of(productOption.getId(), productResponse, productOption.getName(), productOption.getAdditionalPrice());
    }

    private ProductOption findProductOptionById(Long id) {
        return productOptionRepository.findById(id)
                .orElseThrow(() -> new NotFoundElementException(id + "를 가진 상품옵션이 존재하지 않습니다."));
    }
}
