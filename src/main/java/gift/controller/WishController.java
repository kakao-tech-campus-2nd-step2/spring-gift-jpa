package gift.controller;

import gift.dto.member.MemberResponse;
import gift.dto.product.ProductResponse;
import gift.dto.wish.WishCreateRequest;
import gift.dto.wish.WishRequest;
import gift.dto.wish.WishResponse;
import gift.model.Member;
import gift.model.Product;
import gift.service.MemberService;
import gift.service.ProductService;
import gift.service.WishService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wishes")
public class WishController {

    private final WishService wishService;
    private final MemberService memberService;
    private final ProductService productService;

    public WishController(WishService wishService, MemberService memberService,
        ProductService productService) {
        this.wishService = wishService;
        this.memberService = memberService;
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<WishResponse>> getWishlist(
        @RequestAttribute("memberId") Long memberId) {
        List<WishResponse> wishlist = wishService.getWishlistByMemberId(memberId);
        return ResponseEntity.ok(wishlist);
    }

    @PostMapping
    public ResponseEntity<WishResponse> addWish(
        @Valid @RequestBody WishCreateRequest wishCreateRequest,
        @RequestAttribute("memberId") Long memberId) {

        MemberResponse memberResponse = memberService.getMemberById(memberId);
        Member member = memberService.convertToEntity(memberResponse);

        ProductResponse productResponse = productService.getProductById(wishCreateRequest.productId());
        Product product = productService.convertToEntity(productResponse);

        WishRequest wishRequest = new WishRequest(member, product);
        WishResponse createdWish = wishService.addWish(wishRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdWish);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWish(@PathVariable Long id) {
        wishService.deleteWish(id);
        return ResponseEntity.noContent().build();
    }
}
