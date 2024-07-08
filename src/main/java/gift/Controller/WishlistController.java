package gift.Controller;

import gift.model.Wishlist;
import gift.model.WishlistDao;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {
    Long id = 0L;

    private final WishlistDao WishlistDao;
    private final MemberController MemberController;

    public WishlistController(gift.model.WishlistDao wishlistDao, gift.Controller.MemberController memberController) {
        WishlistDao = wishlistDao;
        MemberController = memberController;
    }

    //멤버 id로 해당 멤버의 위시리스트 가져옴
    @GetMapping("/getAllWishlist")
    public List<Wishlist> getWishlist(HttpServletRequest request) throws AuthenticationException {
        //auth로 유저 아이디 가져옴
        Long userid = MemberController.getIdByToken(request);
        //가져온 유저아이디 검색
        List<Wishlist> wishlist = WishlistDao.selectAllWishlist(userid);
        return wishlist;
    }

    //위시리스트 상품 추가
    @PostMapping("/addWishlist/{productid}")
    public void postWishlist(@PathVariable Long productid, HttpServletRequest request) throws AuthenticationException {
        // auth로 유저 아이디 가져옴
        Long userid = MemberController.getIdByToken(request);
        // pathvariable로 상품 아이디 가져옴
        Wishlist wishlist = new Wishlist();
        id++;
        wishlist.setId(id);
        wishlist.setUserId(userid);
        wishlist.setProductId(productid);
        WishlistDao.insertWishlist(wishlist);
    }

    //위시리스크 상품 wishlist id 받아와 삭제
    @DeleteMapping("/deleteWishlist/{id}")
    public void deleteProductController(@PathVariable Long id){
        WishlistDao.deleteWishlist(id);
    }
}