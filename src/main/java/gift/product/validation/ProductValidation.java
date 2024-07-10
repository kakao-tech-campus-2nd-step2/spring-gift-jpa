package gift.product.validation;

import gift.product.dao.ProductDao;
import gift.product.exception.InstanceValueException;
import gift.product.exception.InvalidProductIdException;
import gift.product.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductValidation {
    private final ProductDao productDao;

    @Autowired
    public ProductValidation(ProductDao productDao) {
        this.productDao = productDao;
    }

    public void registerValidation(Product product) {
        if(isNullString(product.getName(), product.getImageUrl()))
            throw new InstanceValueException("변경하는 상품의 상품의 하나 이상의 속성이 누락되었습니다.");
        if(isIncludeNameKakao(product.getName()))
            throw new InstanceValueException("'카카오'가 포함된 문구는 담당 MD와 협의한 경우에만 사용할 수 있습니다.");
        if(!isPositivePrice(product.getPrice()))
            throw new InstanceValueException("상품의 가격은 1 이상의 양의 정수만 가능합니다.");
    }

    public void updateValidation(Long id, Product product) {
        registerValidation(product);
        if(isNullId(id))
            throw new InstanceValueException("수정할 상품의 ID가 누락되었습니다.");
        if(!productDao.existsById(id))
            throw new InvalidProductIdException("변경을 시도하는 상품의 ID가 존재하지 않습니다.");
    }

    public boolean isIncludeNameKakao(String name) {
        return name.contains("카카오");
    }

    public boolean isPositivePrice(int price) {
        return price > 0;
    }

    public boolean isNullId(Long id) {
        return id == null;
    }
    public boolean isNullString(String pName, String pImageUrl) {
        return pName == null || pImageUrl == null;
    }
}
