package gift.util.validator.databaseValidator;

import gift.dto.MemberDTO;
import gift.dto.ProductDTO;
import gift.entity.Member;
import gift.entity.Product;
import gift.exception.BadRequestExceptions.BadRequestException;
import gift.exception.BadRequestExceptions.InvalidIdException;
import gift.exception.BadRequestExceptions.NoSuchProductIdException;
import gift.exception.BadRequestExceptions.UserNotFoundException;
import gift.exception.InternalServerExceptions.InternalServerException;
import gift.repository.MemberRepository;
import gift.repository.ProductRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class ProductDatabaseValidator {
    private final ProductRepository productRepository;

    @Autowired
    public ProductDatabaseValidator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void validateProductParameter(Long id, ProductDTO productDTO) throws BadRequestException {
        if (!Objects.equals(productDTO.id(), id)) {
            throw new InvalidIdException("올바르지 않은 id입니다.");
        }
    }

    Product validateProduct(ProductDTO productDTO) {
        Optional<Product> optionalProduct =
                productRepository.findByIdAndNameAndPriceAndImageUrl(productDTO.id(),
                        productDTO.name(),
                        productDTO.price(), productDTO.imageUrl());
        if (optionalProduct.isEmpty()) {
            throw new BadRequestException("그러한 제품은 없습니다.");
        }

        return optionalProduct.get();
    }

    Product validateProduct(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new NoSuchProductIdException("id가 %d인 제품은 존재하지 않습니다.".formatted(productId));
        }

        return optionalProduct.get();
    }

}
