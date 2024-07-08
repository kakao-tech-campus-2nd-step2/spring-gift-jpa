package gift.product.validation;

import gift.product.service.AdminProductService;
import gift.product.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WishListValidation {
    private final AdminProductService adminProductService;
    private final WishListService wishListService;

    @Autowired
    public WishListValidation(AdminProductService adminProductService, WishListService wishListService) {
        this.adminProductService = adminProductService;
        this.wishListService = wishListService;
    }

    public boolean isExistsProduct(Long id) {
        return adminProductService.existsById(id);
    }

    public boolean isRegisterProduct(Long pId, String email) {
        return wishListService.existsByPId(pId, email);
    }

}
