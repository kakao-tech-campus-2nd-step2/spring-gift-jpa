package gift.service;

import gift.dto.ProductOptionRequest;
import gift.dto.ProductOptionResponse;
import gift.dto.ProductResponse;
import gift.helper.RepositoryReader;
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
    private final RepositoryReader repositoryReader;

    public ProductOptionService(ProductOptionRepository optionRepository, ProductRepository productRepository, RepositoryReader repositoryReader) {
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
        this.repositoryReader = repositoryReader;
    }

    public ProductOptionResponse addOption(ProductOptionRequest productOptionRequest) {
        var option = saveProductOptionWithProductRequest(productOptionRequest);
        return getProductOptionResponseFromProductOption(option);
    }

    public void updateOption(Long id, ProductOptionRequest productOptionRequest) {
        var option = repositoryReader.findEntityById(optionRepository, id);
        option.updateOptionInfo(productOptionRequest.name(), productOptionRequest.additionalPrice());
        optionRepository.save(option);
    }

    public ProductOptionResponse getOption(Long id) {
        var option = repositoryReader.findEntityById(optionRepository, id);
        return getProductOptionResponseFromProductOption(option);
    }

    public List<ProductOptionResponse> getOptions(Long productId) {
        return optionRepository.findAllByProductId(productId)
                .stream()
                .map(this::getProductOptionResponseFromProductOption)
                .toList();
    }

    public void deleteOption(Long id) {
        var productOption = repositoryReader.findEntityById(optionRepository, id);
        productOption.removeOption();
        optionRepository.deleteById(id);
    }

    private ProductOption saveProductOptionWithProductRequest(ProductOptionRequest productOptionRequest) {
        var product = repositoryReader.findEntityById(productRepository, productOptionRequest.productId());
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
