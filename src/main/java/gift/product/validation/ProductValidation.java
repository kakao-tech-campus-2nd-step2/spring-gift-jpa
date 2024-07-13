package gift.product.validation;

import gift.product.dto.ProductDTO;
import gift.product.repository.ProductRepository;
import gift.product.exception.InstanceValueException;
import gift.product.exception.InvalidIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductValidation {
    private final ProductRepository productRepository;

    @Autowired
    public ProductValidation(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void registerValidation(ProductDTO productDTO) {
        System.out.println("[ProductValidation] registerValidation()");
        if(productDTO.getName().contains("카카오"))
            throw new InstanceValueException("'카카오'가 포함된 문구는 담당 MD와 협의한 경우에만 사용할 수 있습니다.");
        if(productDTO.getPrice() <= 0)
            throw new InstanceValueException("상품의 가격은 1 이상의 양의 정수만 가능합니다.");
    }

    public void updateValidation(Long id, ProductDTO productDTO) {
        System.out.println("[ProductValidation] updateValidation()");
        isExistId(id);
        registerValidation(productDTO);
    }
    
    public void isExistId(Long id) {
        System.out.println("[ProductValidation] isExistId()");
        if(!productRepository.existsById(id))
            throw new InvalidIdException("상품의 ID가 존재하지 않습니다.");
    }
}
