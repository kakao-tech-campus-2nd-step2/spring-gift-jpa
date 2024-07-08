package gift.product.validation;

import gift.product.exception.InvalidProductNameException;
import gift.product.service.AdminProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductValidation {
    private final AdminProductService adminProductService;

    @Autowired
    public ProductValidation(AdminProductService adminProductService) {
        this.adminProductService = adminProductService;
    }

    public boolean existsById(Long id) {
        return adminProductService.existsById(id);
    }

    public void isIncludeKakao(String name) {
        if(name.contains("카카오"))
            throw new InvalidProductNameException("'카카오'가 포함된 문구는 담당 MD와 협의한 경우에만 사용할 수 있습니다.");
    }
}
