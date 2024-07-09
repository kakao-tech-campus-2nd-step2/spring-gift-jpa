package gift.product.validation;

import gift.product.dao.WishListDao;
import gift.product.service.AdminProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WishListValidation {
    private final AdminProductService adminProductService;
    private final WishListDao wishListDao;

    @Autowired
    public WishListValidation(AdminProductService adminProductService, WishListDao wishListDao) {
        this.adminProductService = adminProductService;
        this.wishListDao = wishListDao;
    }

    public boolean isExistsProduct(Long id) {
        return adminProductService.existsById(id);
    }

    public boolean isRegisterProduct(Long pId, String email) {
        return wishListDao.existsByPId(pId, email);
    }

}
