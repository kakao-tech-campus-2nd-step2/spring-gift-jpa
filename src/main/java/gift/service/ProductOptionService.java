package gift.service;

import gift.dto.ProductOptionRequest;
import gift.dto.ProductOptionResponse;
import gift.model.ProductOption;
import gift.repository.ProductOptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductOptionService {

    private final ProductOptionRepository optionRepository;

    public ProductOptionService(ProductOptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    public ProductOptionResponse addOption(ProductOptionRequest productOptionRequest) {
        var option = createOptionWithOptionRequest(productOptionRequest);
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
        return optionRepository.findAll(productId)
                .stream()
                .map(this::getProductOptionResponseFromProductOption)
                .toList();
    }

    public void deleteOption(Long id) {
        optionRepository.deleteById(id);
    }

    private ProductOption findOptionWithId(Long id) {
        return optionRepository.findById(id);
    }

    private ProductOption createOptionWithOptionRequest(ProductOptionRequest productOptionRequest) {
        return new ProductOption(productOptionRequest.productId(), productOptionRequest.name(), productOptionRequest.additionalPrice());
    }

    private void updateProductOptionWithId(ProductOption option, ProductOptionRequest productOptionRequest) {
        option.updateOptionInfo(productOptionRequest.name(), productOptionRequest.additionalPrice());
        optionRepository.update(option);
    }

    private ProductOptionResponse getProductOptionResponseFromProductOption(ProductOption productOption) {
        return ProductOptionResponse.of(productOption.getId(), productOption.getProductId(), productOption.getName(), productOption.getAdditionalPrice());
    }
}
