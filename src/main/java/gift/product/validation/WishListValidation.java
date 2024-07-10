package gift.product.validation;

import gift.product.dao.ProductDao;
import gift.product.dao.WishListDao;
import gift.product.exception.InvalidProductIdException;
import gift.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WishListValidation {
    private final ProductService productService;
    private final ProductDao productDao;

    @Autowired
    public WishListValidation(ProductService productService, ProductDao productDao) {
        this.productService = productService;
        this.productDao = productDao;
    }

    public void registerWishProduct(Long id) {
        if(productDao.existsById(id))
            throw new InvalidProductIdException("해당 ID로 등록된 상품이 존재하지 않습니다.");
    }

}
