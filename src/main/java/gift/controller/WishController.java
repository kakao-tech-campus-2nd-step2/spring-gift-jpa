package gift.controller;

import gift.DTO.PageRequestDTO;
import gift.DTO.WishDTO;
import gift.service.WishService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishController {
    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    //멤버 id로 해당 멤버의 위시리스트 가져옴
    @GetMapping("/getAllWishlist")
    public List<WishDTO> getWishlistController(HttpServletRequest request, @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "id") String sortBy,
                                            @RequestParam(defaultValue = "asc") String sortOrder) throws AuthenticationException {
        PageRequestDTO pageRequestDTO = new PageRequestDTO(page, sortBy, sortOrder);
        return wishService.getWishlist(request, pageRequestDTO);
    }

    //위시리스트 상품 추가
    @PostMapping("/addWishlist/{productid}")
    public void postWishlist(@PathVariable Long productid, HttpServletRequest request) throws AuthenticationException {
        wishService.postWishlist(productid, request);
    }

    //위시리스크 상품 wishlist id 받아와 삭제
    @DeleteMapping("/deleteWishlist/{id}")
    public void deleteProductController(@PathVariable Long id){
        wishService.deleteProduct(id);
    }
}