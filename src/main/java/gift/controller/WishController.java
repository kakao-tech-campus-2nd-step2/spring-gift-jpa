package gift.controller;

import gift.model.dto.LoginMemberDto;
import gift.model.dto.WishRequestDto;
import gift.model.dto.WishResponseDto;
import gift.resolver.annotation.LoginMember;
import gift.service.WishService;
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

    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @GetMapping
    public List<WishResponseDto> getWishList(@LoginMember LoginMemberDto loginMemberDto) {
        return wishService.getWishList(loginMemberDto);
    }

    @PostMapping
    public void insertProductToWishList(@RequestBody WishRequestDto wishRequestDto,
        @LoginMember LoginMemberDto loginMemberDto) {
        wishService.addProductToWishList(wishRequestDto, loginMemberDto);
    }

    @PutMapping
    public void updateProductInWishList(@RequestBody WishRequestDto wishRequestDto,
        @LoginMember LoginMemberDto loginMemberDto) {
        wishService.updateProductInWishList(wishRequestDto, loginMemberDto);
    }

    @DeleteMapping
    public void deleteProductInWishList(
        @RequestBody WishRequestDto wishRequestDto,
        @LoginMember LoginMemberDto loginMemberDto) {
        wishService.deleteProductInWishList(wishRequestDto, loginMemberDto);
    }
}
