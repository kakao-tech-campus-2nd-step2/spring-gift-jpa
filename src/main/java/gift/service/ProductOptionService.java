package gift.service;

import gift.dto.ProductBasicInformation;
import gift.dto.ProductOptionRequest;
import gift.dto.ProductOptionResponse;
import gift.exception.NotFoundElementException;
import gift.model.ProductOption;
import gift.repository.ProductOptionRepository;
import gift.repository.ProductRepository;
import org.springframework.data.domain.Pageable;
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

    @Transactional(readOnly = true)
    public ProductOptionResponse getOption(Long id) {
        var productOption = findProductOptionById(id);
        return getProductOptionResponseFromProductOption(productOption);
    }

    @Transactional(readOnly = true)
    public List<ProductOptionResponse> getOptions(Long productId, Pageable pageable) {
        return productOptionRepository.findAllByProductId(productId, pageable)
                .stream()
                .map(this::getProductOptionResponseFromProductOption)
                .toList();
    }

    public void deleteOption(Long id) {
        productOptionRepository.deleteById(id);
    }

    private ProductOption saveProductOptionWithProductRequest(ProductOptionRequest productOptionRequest) {
        var product = productRepository.findById(productOptionRequest.productId())
                .orElseThrow(() -> new NotFoundElementException(productOptionRequest.productId() + "를 가진 상품이 존재하지 않습니다."));
        var productOption = new ProductOption(product, productOptionRequest.name(), productOptionRequest.additionalPrice());
        return productOptionRepository.save(productOption);
    }

    private ProductOptionResponse getProductOptionResponseFromProductOption(ProductOption productOption) {
        var product = productOption.getProduct();
        var productBasicInformation = ProductBasicInformation.of(product.getId(), product.getName(), product.getPrice());
        return ProductOptionResponse.of(productOption.getId(), productBasicInformation, productOption.getName(), productOption.getAdditionalPrice());
    }

    private ProductOption findProductOptionById(Long id) {
        return productOptionRepository.findById(id)
                .orElseThrow(() -> new NotFoundElementException(id + "를 가진 상품옵션이 존재하지 않습니다."));
    }
}
