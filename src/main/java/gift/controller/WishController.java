package gift.controller;

import gift.annotation.LoginMember;
import gift.domain.Member;
import gift.domain.Wish;
import gift.dto.WishDto;
import gift.repository.MemberDao;
import gift.repository.ProductDao;
import gift.repository.WishDao;
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
    private final WishDao wishDao;
    private final ProductDao productDao;
    private final MemberDao memberDao;

    public WishController(WishService wishService, WishDao wishDao, ProductDao productDao,
        MemberDao memberDao) {
        this.wishService = wishService;
        this.wishDao = wishDao;
        this.productDao = productDao;
        this.memberDao = memberDao;
    }

    @GetMapping
    public ResponseEntity<List<Wish>> getWishlist(@LoginMember Member member) {
        List<Wish> wishItems = wishDao.findByMemberId(member.getId());
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
        wishService.deleteWishbyId(wishId);
        return ResponseEntity.status(HttpStatus.OK).body("위시 삭제 성공");
    }

}
