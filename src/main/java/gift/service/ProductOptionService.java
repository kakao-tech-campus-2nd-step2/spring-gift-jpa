package gift.service;

import gift.dto.ProductOptionRequest;
import gift.dto.ProductOptionResponse;
import gift.exception.NotFoundElementException;
import gift.model.ProductOption;
import gift.repository.ProductOptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductOptionService {

    private final ProductOptionRepository optionRepository;
    private final ProductService productService;

    public ProductOptionService(ProductOptionRepository optionRepository, ProductService productService) {
        this.optionRepository = optionRepository;
        this.productService = productService;
    }

    public ProductOptionResponse addOption(ProductOptionRequest productOptionRequest) {
        var option = createProductOptionWithProductRequest(productOptionRequest);
        var savedOption = optionRepository.save(option);
        return getProductOptionResponseFromProductOption(savedOption);
    }

    public void updateOption(Long id, ProductOptionRequest productOptionRequest) {
        var option = findOptionWithId(id);
        updateProductOptionWithId(option, productOptionRequest);
    }

    public ProductOptionResponse getOption(Long id) {
        var option = findOptionWithId(id);
        return getProductOptionResponseFromProductOption(option);
    }

    public List<ProductOptionResponse> getOptions(Long productId) {
        return optionRepository.findAllByProductId(productId)
                .stream()
                .map(this::getProductOptionResponseFromProductOption)
                .toList();
    }

    public void deleteOption(Long id) {
        var productOption = findOptionWithId(id);
        productOption.removeOption();
        optionRepository.deleteById(id);
    }

    private ProductOption findOptionWithId(Long id) {
        var productOption = optionRepository.findById(id);
        if (productOption.isEmpty()) throw new NotFoundElementException("존재하지 않는 리소스에 대한 접근입니다.");
        return productOption.get();
    }

    private ProductOption createProductOptionWithProductRequest(ProductOptionRequest productOptionRequest) {
        var product = productService.findProductWithId(productOptionRequest.productId());
        var option = new ProductOption(productOptionRequest.name(), productOptionRequest.additionalPrice());
        option.addProduct(product);
        return option;
    }

    private void updateProductOptionWithId(ProductOption option, ProductOptionRequest productOptionRequest) {
        option.updateOptionInfo(productOptionRequest.name(), productOptionRequest.additionalPrice());
        optionRepository.save(option);
    }

    private ProductOptionResponse getProductOptionResponseFromProductOption(ProductOption productOption) {
        var product = productService.getProduct(productOption.getProduct().getId());
        return ProductOptionResponse.of(productOption.getId(), product, productOption.getName(), productOption.getAdditionalPrice());
    }
}
