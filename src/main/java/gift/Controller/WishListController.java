package gift.Controller;

import gift.Model.RequestWishListDTO;
import gift.Model.ResponseWishListDTO;
import gift.Model.Member;
import gift.Service.WishListService;
import gift.annotation.ValidUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class WishListController {
    private final WishListService wishListService;


    public WishListController (WishListService wishListService){
        this.wishListService = wishListService;
    }

    @PostMapping("/wishList")
    public void addWishList(@ValidUser Member member, @RequestBody RequestWishListDTO requestWishListDTO){
        wishListService.addWishList(member, requestWishListDTO);
    }

    @GetMapping("/wishList")
    public ResponseEntity<Map<String, List<ResponseWishListDTO>>> getWishList(@ValidUser Member member){
        List<ResponseWishListDTO> list= wishListService.getWishList(member);
        Map<String, List<ResponseWishListDTO>> response = new HashMap<>();
        response.put("wishlist", list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/wishList")
    public ResponseEntity<Map<String, List<ResponseWishListDTO>>> editWishList(@ValidUser Member member, @RequestBody RequestWishListDTO requestWishListDTO){
        List<ResponseWishListDTO> list = wishListService.editWishList(member, requestWishListDTO);
        Map<String, List<ResponseWishListDTO>> response = new HashMap<>();
        response.put("wishlist", list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/wishList")
    public ResponseEntity<Map<String, List<ResponseWishListDTO>>> deleteWishList(@ValidUser Member member, @RequestBody RequestWishListDTO requestWishListDTO) {
        List<ResponseWishListDTO> list = wishListService.deleteWishList(member, requestWishListDTO);
        Map<String, List<ResponseWishListDTO>> response = new HashMap<>();
        response.put("wishlist", list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}