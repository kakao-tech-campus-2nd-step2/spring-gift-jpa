package gift.controller;

import gift.domain.WishList.WishListRequest;
import gift.domain.member.MemberResponse;
import gift.domain.product.Product;
import gift.service.WishListService;
import gift.util.AuthenticatedUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wishes")
public class WishListController {
    private final WishListService wishListService;

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    @GetMapping
    @AuthenticatedUser
    public List<Product> readWishList(HttpServletRequest httpServletRequest) {
        MemberResponse member = (MemberResponse) httpServletRequest.getAttribute("authenticatedMember");
        return wishListService.read(member.id());
    }

    @PutMapping
    @AuthenticatedUser
    public void addWishList(HttpServletRequest httpServletRequest, @Valid @RequestBody
        WishListRequest wishListRequest) {
        MemberResponse member = (MemberResponse)httpServletRequest.getAttribute("authenticatedMember");
        wishListService.create(member.id(), wishListRequest.productId());
    }

    @DeleteMapping
    @AuthenticatedUser
    public void deleteWishList(HttpServletRequest httpServletRequest, @Valid @RequestBody
    WishListRequest wishListRequest) {
        MemberResponse member = (MemberResponse)httpServletRequest.getAttribute("authenticatedMember");
        wishListService.delete(member.id(), wishListRequest.productId());
    }
}
