package gift.util.validator.databaseValidator;

import gift.dto.MemberDTO;
import gift.dto.ProductDTO;
import gift.entity.Member;
import gift.entity.Product;
import gift.exception.BadRequestExceptions.BadRequestException;
import gift.exception.InternalServerExceptions.InternalServerException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WishListFieldDatabaseValidator {
    private final ProductDatabaseValidator productDatabaseValidator;
    private final MemberDatabaseValidator memberDatabaseValidator;

    @Autowired
    public WishListFieldDatabaseValidator(ProductDatabaseValidator productDatabaseValidator,
            MemberDatabaseValidator memberDatabaseValidator) {
        this.productDatabaseValidator = productDatabaseValidator;
        this.memberDatabaseValidator = memberDatabaseValidator;
    }

    public Map<String, Object> validateProductParameter(
            MemberDTO memberDTO, ProductDTO productDTO)
            throws BadRequestException, InternalServerException {
        Member member = memberDatabaseValidator.validateMember(memberDTO);
        Product product = productDatabaseValidator.validateProduct(productDTO);

        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("member", member);
        returnMap.put("product", product);

        return returnMap;
    }

    public Map<String, Object> validateProductParameter(MemberDTO memberDTO, Long id)
            throws BadRequestException {
        Member member = memberDatabaseValidator.validateMember(memberDTO);
        Product product = productDatabaseValidator.validateProduct(id);

        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("member", member);
        returnMap.put("product", product);

        return returnMap;
    }
}