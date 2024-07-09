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
import java.util.Objects;

@RestController
@RequestMapping("/wishlists")
public class WishListController {
    private final WishListService wishListService;
    private final JwtService jwtService;

    public WishListController(WishListService wishListService, JwtService jwtService){
        this.wishListService = wishListService;
        this.jwtService = jwtService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(
            @RequestHeader("Authorization") String token,
            @RequestParam("menuId") Long menuId
    ) {
        String jwtId = jwtService.getMemberId();
        WishListRequest wishListRequest = new WishListRequest(jwtId,menuId);
        wishListService.create(wishListRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization",token.replace("Bearer ",""));
        return ResponseEntity.ok().headers(headers).body("success");
    }

    @GetMapping("/read")
    public ResponseEntity<HashMap<String,Object>> read(){
        String jwtId = jwtService.getMemberId();
        if(jwtId == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        else{
            List<WishListResponse> nowWishList = wishListService.findById(jwtId);
            HashMap<String,Object> answer = new HashMap<>();
            answer.put("data",nowWishList);
            return ResponseEntity.ok().body(answer);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(
            @RequestParam("menuId") Long menuId
    ){
        String jwtId = jwtService.getMemberId();
        if(jwtId == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("권한이 없습니다.");
        }
        else{
            if(wishListService.delete(jwtId,menuId)){
                return ResponseEntity.ok().body("성공적으로 삭제되었습니다.");
            }
            else{
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("해당 메뉴가 존재하지 않습니다.");
            }
        }
    }
}
