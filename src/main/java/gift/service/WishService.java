package gift.service;

import gift.dao.WishDao;
import gift.model.Wish;
import gift.repository.WishRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.List;

@Service
public class WishService {
    private final MemberService memberService;
    private final WishRepository wishRepository;

    public WishService(MemberService memberService, WishRepository wishRepository) {
        this.memberService = memberService;
        this.wishRepository = wishRepository;
    }

    public List<Wish> getWishlistController(HttpServletRequest request) throws AuthenticationException {
        //auth로 유저 아이디 가져옴
        long memberId = memberService.getIdByToken(request);
        //가져온 유저아이디 검색
        return wishRepository.findByMemberId(memberId);
    }

    public void postWishlist(Long productid, HttpServletRequest request) throws AuthenticationException {
        // auth로 유저 아이디 가져옴
        long memberId = memberService.getIdByToken(request);
        // pathvariable로 상품 아이디 가져옴
        Wish wish = new Wish(memberId, productid);
        wishRepository.save(wish);
    }

    public void deleteProduct(Long id){
        wishRepository.deleteById(id);
    }
}
