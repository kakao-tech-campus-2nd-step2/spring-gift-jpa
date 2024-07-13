package gift.controller;

import gift.domain.WishListRequest;
import gift.domain.WishListResponse;
import gift.repository.MemberRepository;
import gift.repository.MenuRepository;
import gift.service.JwtService;
import gift.service.WishListService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlists")
public class WishListController {
    private final WishListService wishListService;
    private final JwtService jwtService;
    private final MenuRepository menuRepository;
    private final MemberRepository memberRepository;

    public WishListController(WishListService wishListService, JwtService jwtService, MenuRepository menuRepository, MemberRepository memberRepository){
        this.wishListService = wishListService;
        this.jwtService = jwtService;
        this.menuRepository = menuRepository;
        this.memberRepository = memberRepository;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(
            @RequestHeader("Authorization") String token,
            @RequestParam("menuId") Long menuId
    ) {
        String jwtId = jwtService.getMemberId();
        WishListRequest wishListRequest = new WishListRequest(
                memberRepository.findById(jwtId).get(),
                menuRepository.findById(menuId).get());
        wishListService.save(wishListRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization",token.replace("Bearer ",""));
        return ResponseEntity.ok().headers(headers).body("success");
    }

    @GetMapping("/read")
    public ResponseEntity<List<WishListResponse>> read(){
        String jwtId = jwtService.getMemberId();
        List<WishListResponse> nowWishList = wishListService.findById(jwtId);
        return ResponseEntity.ok().body(nowWishList);
    }

    @DeleteMapping
    public ResponseEntity<String> delete(
            @RequestParam("Id") Long id
    ){
        jwtService.getMemberId();
        wishListService.delete(id);
        return ResponseEntity.ok().body("성공적으로 삭제되었습니다.");
    }
}
