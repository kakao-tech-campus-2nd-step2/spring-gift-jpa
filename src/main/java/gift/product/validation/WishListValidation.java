package gift.product.validation;

import gift.product.repository.WishListRepository;
import gift.product.exception.InvalidIdException;
import gift.product.exception.UnauthorizedException;
import gift.product.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class WishListValidation {
    private final WishListRepository wishListRepository;

    @Autowired
    public WishListValidation(WishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    public void deleteValidation(Long id, Member member) {
        System.out.println("[WishListValidation] deleteValidation()");
        if(wishListRepository.findById(id).isPresent())
            throw new InvalidIdException("요청한 id가 위시리스트에 존재하지 않아 삭제할 수 없습니다.");
        if(!Objects.equals(wishListRepository.findById(id).get().getMember().getId(), member.getId()))
            throw new UnauthorizedException("본인의 위시 리스트만 삭제가 가능합니다.");
    }

}
