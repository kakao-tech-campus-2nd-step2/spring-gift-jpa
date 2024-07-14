package gift.Controller;

import gift.Model.RequestWishDTO;
import gift.Model.ResponseWishDTO;
import gift.Model.Member;
import gift.Service.WishService;
import gift.annotation.ValidUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class WishController {
    private final WishService wishService;


    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @PostMapping("/wishList")
    public void addWish(@ValidUser Member member, @RequestBody RequestWishDTO requestWishDTO) {
        wishService.addWish(member, requestWishDTO);
    }

    @GetMapping("/wishList")
    public ResponseEntity<Map<String, List<ResponseWishDTO>>> getWish(@ValidUser Member member) {
        List<ResponseWishDTO> list = wishService.getWish(member);
        Map<String, List<ResponseWishDTO>> response = new HashMap<>();
        response.put("wish", list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/wishList")
    public ResponseEntity<Map<String, List<ResponseWishDTO>>> editWish(@ValidUser Member member, @RequestBody RequestWishDTO requestWishDTO) {
        List<ResponseWishDTO> list = wishService.editWish(member, requestWishDTO);
        Map<String, List<ResponseWishDTO>> response = new HashMap<>();
        response.put("wish", list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/wishList")
    public ResponseEntity<Map<String, List<ResponseWishDTO>>> deleteWish(@ValidUser Member member, @RequestBody RequestWishDTO requestWishDTO) {
        List<ResponseWishDTO> list = wishService.deleteWish(member, requestWishDTO);
        Map<String, List<ResponseWishDTO>> response = new HashMap<>();
        response.put("wish", list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}