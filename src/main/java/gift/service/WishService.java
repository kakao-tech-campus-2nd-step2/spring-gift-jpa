package gift.service;

import gift.dao.WishDao;
import gift.model.Wish;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.List;

@Service
public class WishService {
    private final MemberService memberService;
    private final WishDao wishDao;

    public WishService(MemberService memberService, WishDao wishDao) {
        this.memberService = memberService;
        this.wishDao = wishDao;
    }

    public List<Wish> getWishlistController(HttpServletRequest request) throws AuthenticationException {
        //auth로 유저 아이디 가져옴
        Long userid = memberService.getIdByToken(request);
        //가져온 유저아이디 검색
        List<Wish> wish = wishDao.selectAllWishlist(userid);
        return wish;
    }

    public void postWishlist(Long productid, HttpServletRequest request) throws AuthenticationException {
        // auth로 유저 아이디 가져옴
        Long userid = memberService.getIdByToken(request);
        // pathvariable로 상품 아이디 가져옴
        Wish wish = new Wish(userid, productid);

        wishDao.insertWishlist(wish);
    }

    public void deleteProduct(Long id){
        wishDao.deleteWishlist(id);
    }
}
