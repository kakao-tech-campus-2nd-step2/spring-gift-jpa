package gift.controller;

import gift.dto.TokenLoginRequestDTO;
import gift.entity.Wish;
import gift.service.LoginMember;
import gift.service.WishService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishes")
public class WishController {
    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @PostMapping("/post")
    public ResponseEntity<String> addWish(@RequestParam("productId") Long productId,
                                          @LoginMember TokenLoginRequestDTO tokenLoginRequestDTO) {
        System.out.println("post");
        wishService.addWish(productId, tokenLoginRequestDTO);
        return ResponseEntity.ok("Wish added");
    }

    @GetMapping("/get")
    public ResponseEntity<List<Wish>> getWishes(@LoginMember TokenLoginRequestDTO tokenLoginRequestDTO) {
        System.out.println("get");
        List<Wish> Wishes =  wishService.getWishesByMemberId(tokenLoginRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).
                body(Wishes);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteWish(@PathVariable("productId") Long productId,
                                             @LoginMember TokenLoginRequestDTO loginUser) {
        System.out.println("delete");
        wishService.removeWish(productId, loginUser);
        return ResponseEntity.ok("Wish deleted");
    }

}
