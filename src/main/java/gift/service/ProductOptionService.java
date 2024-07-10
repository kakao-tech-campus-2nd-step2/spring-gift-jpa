package gift.service;

import gift.dto.ProductOptionRequest;
import gift.dto.ProductOptionResponse;
import gift.dto.ProductResponse;
import gift.model.ProductOption;
import gift.repository.ProductOptionRepository;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductOptionService {

    private final ProductOptionRepository optionRepository;
    private final ProductRepository productRepository;


    public ProductOptionService(ProductOptionRepository optionRepository, ProductRepository productRepository) {
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
    }

    public ProductOptionResponse addOption(ProductOptionRequest productOptionRequest) {
        var option = saveProductOptionWithProductRequest(productOptionRequest);
        return getProductOptionResponseFromProductOption(option);
    }

    public void updateOption(Long id, ProductOptionRequest productOptionRequest) {
        var option = optionRepository.findByIdOrThrow(id);
        option.updateOptionInfo(productOptionRequest.name(), productOptionRequest.additionalPrice());
        optionRepository.save(option);
    }

    public ProductOptionResponse getOption(Long id) {
        var option = optionRepository.findByIdOrThrow(id);
        return getProductOptionResponseFromProductOption(option);
    }

    public List<ProductOptionResponse> getOptions(Long productId) {
        return optionRepository.findAllByProductId(productId)
                .stream()
                .map(this::getProductOptionResponseFromProductOption)
                .toList();
    }

    public void deleteOption(Long id) {
        var option = optionRepository.findByIdOrThrow(id);
        option.removeOption();
        optionRepository.deleteById(id);
    }

    private ProductOption saveProductOptionWithProductRequest(ProductOptionRequest productOptionRequest) {
        var product = productRepository.findByIdOrThrow(productOptionRequest.productId());
        var option = new ProductOption(productOptionRequest.name(), productOptionRequest.additionalPrice());
        option.addProduct(product);
        var savedOption = optionRepository.save(option);
        return savedOption;
    }

    private ProductOptionResponse getProductOptionResponseFromProductOption(ProductOption productOption) {
        var product = productOption.getProduct();
        var productResponse = ProductResponse.of(product.getId(), product.getName(), product.getPrice(), product.getImageUrl());
        return ProductOptionResponse.of(productOption.getId(), productResponse, productOption.getName(), productOption.getAdditionalPrice());
    }
}
