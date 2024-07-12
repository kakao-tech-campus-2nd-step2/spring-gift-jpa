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
    private final WishService WishService;


    public WishController(WishService WishService) {
        this.WishService = WishService;
    }

    @PostMapping("/Wish")
    public void addWish(@ValidUser Member member, @RequestBody RequestWishDTO requestWishDTO) {
        WishService.addWish(member, requestWishDTO);
    }

    @GetMapping("/Wish")
    public ResponseEntity<Map<String, List<ResponseWishDTO>>> getWish(@ValidUser Member member) {
        List<ResponseWishDTO> list = WishService.getWish(member);
        Map<String, List<ResponseWishDTO>> response = new HashMap<>();
        response.put("Wish", list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/Wish")
    public ResponseEntity<Map<String, List<ResponseWishDTO>>> editWish(@ValidUser Member member, @RequestBody RequestWishDTO requestWishDTO) {
        List<ResponseWishDTO> list = WishService.editWish(member, requestWishDTO);
        Map<String, List<ResponseWishDTO>> response = new HashMap<>();
        response.put("Wish", list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/Wish")
    public ResponseEntity<Map<String, List<ResponseWishDTO>>> deleteWish(@ValidUser Member member, @RequestBody RequestWishDTO requestWishDTO) {
        List<ResponseWishDTO> list = WishService.deleteWish(member, requestWishDTO);
        Map<String, List<ResponseWishDTO>> response = new HashMap<>();
        response.put("Wish", list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}