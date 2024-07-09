package gift.controller;

import gift.annotation.LoginMember;
import gift.domain.Member;
import gift.domain.Wish;
import gift.dto.WishDto;
import gift.repository.MemberRepository;
import gift.repository.WishRepository;
import gift.repository.ProductRepository;
import gift.service.WishService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wishlist")
public class WishController {
    private final WishService wishService;
    private final WishRepository wishRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    public WishController(WishService wishService, WishRepository wishRepository,
        ProductRepository productRepository, MemberRepository memberRepository) {
        this.wishService = wishService;
        this.wishRepository = wishRepository;
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
    }


    @GetMapping
    public ResponseEntity<List<Wish>> getWishlist(@LoginMember Member member) {
        List<Wish> wishItems = wishRepository.findByMemberId(member.getId());
//        List<Product> products = new ArrayList<>();
//        for (Wish wishItem : wishItems) {
//            Product product = productDao.findById(wishItem.getProductId());
//            if (product != null) {
//                products.add(product);
//            }
//        }
        return new ResponseEntity<>(wishItems, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addWish(@Valid @RequestBody WishDto wishDto, @LoginMember Member member) {
        wishService.addWish(member.getId(), wishDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("위시 추가 성공");
    }

    @PostMapping("/delete/{wishId}")
    public ResponseEntity<String> deleteWish(@PathVariable("wishId") Long wishId, @LoginMember Member member) {
        wishService.deleteWish(wishId);
        return ResponseEntity.status(HttpStatus.OK).body("위시 삭제 성공");
    }

}
