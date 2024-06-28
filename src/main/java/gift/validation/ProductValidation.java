package gift.validation;

import gift.dto.CreateProductDto;
import gift.repository.ProductRepository;

public class ProductValidation {

    ProductRepository productRepository;
    public ProductValidation(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void validateProductDto(CreateProductDto productDto) {
        if (productDto.getName() == null || productDto.getDescription() == null || productDto.getPrice() == null || productDto.getImageUrl() == null) {
            throw new IllegalArgumentException("상품의 이름, 가격, 설명을 모두 입력해야합니다.");
        }
    }

    public void validateProductExists(Long productId) {
        if (productRepository.findById(productId) == null) {
            throw new IllegalArgumentException("일치하는 상품이 없습니다.");
        }
    }

}
