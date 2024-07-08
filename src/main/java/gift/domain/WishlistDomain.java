package gift.domain;

import gift.dao.WishlistDao;
import gift.model.Wishlist;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;
import java.util.List;

@Component
public class WishlistDomain {
    private final MemberDomain memberDomain;
    private final WishlistDao wishlistDao;

    public WishlistDomain(MemberDomain memberDomain, WishlistDao wishlistDao) {
        this.memberDomain = memberDomain;
        this.wishlistDao = wishlistDao;
    }

    public List<Wishlist> getWishlistController(HttpServletRequest request) throws AuthenticationException {
        //auth로 유저 아이디 가져옴
        Long userid = memberDomain.getIdByToken(request);
        //가져온 유저아이디 검색
        List<Wishlist> wishlist = wishlistDao.selectAllWishlist(userid);
        return wishlist;
    }

    public void postWishlist(Long productid, HttpServletRequest request) throws AuthenticationException {
        // auth로 유저 아이디 가져옴
        Long userid = memberDomain.getIdByToken(request);
        // pathvariable로 상품 아이디 가져옴
        Wishlist wishlist = new Wishlist(userid, productid);

        wishlistDao.insertWishlist(wishlist);
    }

    public void deleteProduct(Long id){
        wishlistDao.deleteWishlist(id);
    }
}
