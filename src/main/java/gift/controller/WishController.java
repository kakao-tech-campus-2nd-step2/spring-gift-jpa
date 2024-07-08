package gift.controller;

import gift.model.Wish;
import gift.model.dto.LoginMemberDto;
import gift.model.dto.WishRequestDto;
import gift.model.dto.WishResponseDto;
import gift.repository.WishDao;
import gift.resolver.annotation.LoginMember;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wishes")
public class WishController {

    private final WishDao wishDao;

    public WishController(WishDao wishDao) {
        this.wishDao = wishDao;
    }

    @GetMapping
    public List<WishResponseDto> getWishList(@LoginMember LoginMemberDto loginMemberDto) {
        return wishDao.selectAllWishesByMemberId(loginMemberDto.getId())
            .stream()
            .map(WishResponseDto::from)
            .toList();
    }

    @PostMapping
    public void addProductToWishList(@RequestBody WishRequestDto wishRequestDto,
        @LoginMember LoginMemberDto loginMemberDto) {
        Wish wish = wishRequestDto.toEntity();
        wish.setMemberId(loginMemberDto.getId());
        wishDao.insertWish(wish);
    }

    @PutMapping
    public void updateProductInWishList(@RequestBody WishRequestDto wishRequestDto,
        @LoginMember LoginMemberDto loginMemberDto) {
        Wish wish = wishRequestDto.toEntity();
        wish.setMemberId(loginMemberDto.getId());
        if (wish.getCount() == 0) {
            wishDao.deleteWish(wish);
            return;
        }
        wishDao.updateWish(wish);
    }

    @DeleteMapping
    public void deleteProductInWishList(
        @RequestBody WishRequestDto wishRequestDto,
        @LoginMember LoginMemberDto loginMemberDto) {
        Wish wish = wishRequestDto.toEntity();
        wish.setMemberId(loginMemberDto.getId());
        wishDao.deleteWish(wish);
    }
}
