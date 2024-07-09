package gift.controller;

import gift.domain.WishListRequest;
import gift.domain.WishListResponse;
import gift.service.JwtService;
import gift.service.WishListService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/wishlists")
public class WishListController {
    private final WishListService wishListService;
    private final JwtService jwtService;

    public WishListController(WishListService wishListService, JwtService jwtService){
        this.wishListService = wishListService;
        this.jwtService = jwtService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> create(
            @RequestHeader("Authorization") String token,
            @RequestParam("menuId") Long menuId
    ) {
        String jwtId = jwtService.getMemberId();
        WishListRequest wishListRequest = new WishListRequest(jwtId,menuId);
        wishListService.save(wishListRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization",token.replace("Bearer ",""));
        return ResponseEntity.ok().headers(headers).body("success");
    }

    @GetMapping("/read")
    public ResponseEntity<List<WishListResponse>> read(){
        String jwtId = jwtService.getMemberId();
        if(jwtId == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        else{
            List<WishListResponse> nowWishList = wishListService.findById(jwtId);
            return ResponseEntity.ok().body(nowWishList);
        }
    }

    @DeleteMapping
    public ResponseEntity<String> delete(
            @RequestParam("Id") Long id
    ){
        String jwtId = jwtService.getMemberId();
        if(jwtId == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("권한이 없습니다.");
        }
        else{
            wishListService.delete(id);
            return ResponseEntity.ok().body("성공적으로 삭제되었습니다.");
        }
    }
}
