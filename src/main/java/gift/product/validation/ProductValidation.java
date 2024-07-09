package gift.product.validation;

import gift.product.dao.ProductDao;
import gift.product.exception.DuplicateException;
import gift.product.exception.InstanceValueException;
import gift.product.exception.InvalidProductIdException;
import gift.product.exception.InvalidProductNameException;
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
        if(isNullInstance(product))
            throw new InstanceValueException("상품의 속성을 모두 입력해주세요. (상품의 가격은 0이 될 수 없습니다.)");
        if(productDao.existsById(product.getId()))
            throw new DuplicateException("등록하려는 상품의 ID가 이미 존재합니다.");
        if(isIncludeNameKakao(product.getName()))
            throw new InvalidProductNameException("'카카오'가 포함된 문구는 담당 MD와 협의한 경우에만 사용할 수 있습니다.");
        if(!isPositivePrice(product.getPrice()))
            throw new InstanceValueException("상품의 가격은 1 이상의 양의 정수만 가능합니다.");
    }

    public void updateValidation(Product product) {
        if(productDao.existsById(product.getId()))
            throw new InvalidProductIdException("변경을 시도하는 상품의 ID가 존재하지 않습니다.");
        if(isNullInstance(product))
            throw new InstanceValueException("변경하는 상품의 상품의 하나 이상의 속성이 누락되었습니다. (상품의 가격은 0이 될 수 없습니다.)");
        if(isIncludeNameKakao(product.getName()))
            throw new InvalidProductNameException("'카카오'가 포함된 문구는 담당 MD와 협의한 경우에만 사용할 수 있습니다.");
        if(!isPositivePrice(product.getPrice()))
            throw new InstanceValueException("상품의 가격은 1 이상의 양의 정수만 가능합니다.");
    }

    public boolean isIncludeNameKakao(String name) {
        return name.contains("카카오");
    }

    public boolean isPositivePrice(int price) {
        return price > 0;
    }

    public boolean isNullInstance(Product product) {
        if(product.getId() == null)
            return true;
        if(product.getName() == null)
            return true;
        if(product.getPrice() == 0)
            return true;
        if(product.getImageUrl() == null)
            return true;
        return false;
    }
}
