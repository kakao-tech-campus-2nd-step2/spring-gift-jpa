package gift.service;

import gift.dto.MemberDTO;
import gift.dto.ProductDTO;
import gift.entity.Member;
import gift.entity.Product;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntityValidator {
    private final Validator entityValidator;

    @Autowired
    public EntityValidator(Validator entityValidator) {
        this.entityValidator = entityValidator;
    }

    public Product validateProduct(ProductDTO productDTO) throws ConstraintViolationException {
        Product product = new Product(productDTO);
        Set<ConstraintViolation<Product>> violations = entityValidator.validate(product);
        if(!violations.isEmpty())
            throw new ConstraintViolationException(violations);

        return product;
    }

    public Member validateMember(MemberDTO memberDTO) throws ConstraintViolationException {
        Member member = new Member(memberDTO);
        Set<ConstraintViolation<Member>> violations = entityValidator.validate(member);
        if(!violations.isEmpty())
            throw new ConstraintViolationException(violations);

        return member;
    }


}
