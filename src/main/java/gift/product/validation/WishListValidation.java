package gift.product.validation;

import gift.product.dao.ProductDao;
import gift.product.dao.WishListDao;
import gift.product.exception.InvalidIdException;
import gift.product.exception.UnauthorizedException;
import gift.product.model.Member;
import gift.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class WishListValidation {
    private final WishListDao wishListDao;

    @Autowired
    public WishListValidation(WishListDao wishListDao) {
        this.wishListDao = wishListDao;
    }

    public void deleteValidation(Long id, Member member) {
        System.out.println("[WishListValidation] identification()");
        if(wishListDao.findById(id).isPresent())
            throw new InvalidIdException("요청한 id가 위시리스트에 존재하지 않아 삭제할 수 없습니다.");
        if(!Objects.equals(wishListDao.findById(id).get().getMember().getId(), member.getId()))
            throw new UnauthorizedException("본인의 위시 리스트만 삭제가 가능합니다.");
    }

}
