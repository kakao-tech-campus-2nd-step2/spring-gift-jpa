package gift.service;

import gift.model.Wish;
import gift.model.dto.LoginMemberDto;
import gift.model.dto.WishRequestDto;
import gift.model.dto.WishResponseDto;
import gift.repository.WishDao;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WishService {

    private final WishDao wishDao;

    public WishService(WishDao wishDao) {
        this.wishDao = wishDao;
    }

    public List<WishResponseDto> getWishList(LoginMemberDto loginMemberDto) {
        return wishDao.selectAllWishesByMemberId(loginMemberDto.getId())
            .stream()
            .map(WishResponseDto::from)
            .toList();
    }

    public void addProductToWishList(WishRequestDto wishRequestDto, LoginMemberDto loginMemberDto) {
        wishDao.insertWish(wishRequestDto.toEntity(loginMemberDto.getId()));
    }

    public void updateProductInWishList(WishRequestDto wishRequestDto,
        LoginMemberDto loginMemberDto) {
        Wish wish = wishRequestDto.toEntity(loginMemberDto.getId());
        if (wish.getCount() == 0) {
            wishDao.deleteWish(wish);
            return;
        }
        wishDao.updateWish(wish);
    }

    public void deleteProductInWishList(WishRequestDto wishRequestDto,
        LoginMemberDto loginMemberDto) {
        wishDao.deleteWish(wishRequestDto.toEntity(loginMemberDto.getId()));
    }
}
