package gift.service;

import gift.dao.WishlistDao;
import gift.model.Wishlist;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.List;

@Service
public class WishlistService {
    private final MemberService memberService;
    private final WishlistDao wishlistDao;

    public WishlistService(MemberService memberService, WishlistDao wishlistDao) {
        this.memberService = memberService;
        this.wishlistDao = wishlistDao;
    }

    public List<Wishlist> getWishlistController(HttpServletRequest request) throws AuthenticationException {
        //auth로 유저 아이디 가져옴
        Long userid = memberService.getIdByToken(request);
        //가져온 유저아이디 검색
        List<Wishlist> wishlist = wishlistDao.selectAllWishlist(userid);
        return wishlist;
    }

    public void postWishlist(Long productid, HttpServletRequest request) throws AuthenticationException {
        // auth로 유저 아이디 가져옴
        Long userid = memberService.getIdByToken(request);
        // pathvariable로 상품 아이디 가져옴
        Wishlist wishlist = new Wishlist(userid, productid);

        wishlistDao.insertWishlist(wishlist);
    }

    public void deleteProduct(Long id){
        wishlistDao.deleteWishlist(id);
    }
}
