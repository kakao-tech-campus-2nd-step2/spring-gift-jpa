package gift.product.validation;

import gift.product.dao.WishListDao;
import gift.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WishListValidation {
    private final ProductService productService;
    private final WishListDao wishListDao;

    @Autowired
    public WishListValidation(ProductService productService, WishListDao wishListDao) {
        this.productService = productService;
        this.wishListDao = wishListDao;
    }

    public boolean isExistsProduct(Long id) {
        return productService.existsById(id);
    }

    public boolean isRegisterProduct(Long pId, String email) {
        return wishListDao.existsByPId(pId, email);
    }

}
