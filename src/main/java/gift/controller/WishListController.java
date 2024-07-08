package gift.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import gift.Util.JWTUtil;
import gift.entity.WishList;
import gift.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class WishListController {
    @Autowired
    WishListService wishListService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/wishlist/{product_id}")
    public String addWishList(@RequestHeader("Authorization") String token, @PathVariable int product_id){
        wishListService.add(token, product_id);
        return "redirect:/wishlist";
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/wishlist")
    @ResponseBody
    public String getWishList(@RequestHeader("Authorization") String token) throws JsonProcessingException {
        return wishListService.getWishList(token);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/wishlist/{product_id}")
    public String deleteWishList(@RequestHeader("Authorization") String token, @PathVariable int product_id){
        wishListService.deleteWishList(token,product_id);
        return "redirect:/wishlist";
    }

}
