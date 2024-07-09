package gift.service;

import gift.constants.ErrorMessage;
import gift.dto.Member;
import gift.dto.Product;
import gift.jwt.JwtUtil;
import gift.repository.MemberDao;
import gift.repository.WishlistDao;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberDao memberDao;
    private final WishlistDao wishlistDao;
    private final JwtUtil jwtUtil;

    public MemberService(MemberDao memberDao, WishlistDao wishlistDao, JwtUtil jwtUtil) {
        this.memberDao = memberDao;
        this.wishlistDao = wishlistDao;
        this.jwtUtil = jwtUtil;
    }

    /**
     * 회원 가입. <br> 이미 존재하는 email 이면, IllegalArgumentException
     *
     * @param member
     */
    public void registerMember(Member member) {
        memberDao.findByEmail(member.getEmail())
            .ifPresent(user -> {
                throw new IllegalArgumentException(ErrorMessage.EMAIL_ALREADY_EXISTS_MSG);
            });
        memberDao.register(member);
    }

    /**
     * 로그인. <br> email이 일치하지 않으면 NoSuchElementException <br> password가 일치하지 않으면
     * IllegalArgumentException
     *
     * @param member
     * @return
     */
    public String login(Member member) {
        Member queriedMember = memberDao.findByEmail(member.getEmail())
            .orElseThrow(() -> new NoSuchElementException(ErrorMessage.MEMBER_NOT_EXISTS_MSG));
        if (!queriedMember.isCorrectPassword(member.getPassword())) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_PASSWORD_MSG);
        }
        return jwtUtil.createJwt(member.getEmail(), 1000 * 60 * 30);
    }

    /**
     * 회원의 위시 리스트 조회
     *
     * @param email
     * @return
     */
    public List<Product> getAllWishlist(String email) {
        return wishlistDao.findByEmail(email);
    }

    /**
     * 위시 리스트에 상품 추가
     *
     * @param email
     * @param productId
     */
    public void addWishlist(String email, Long productId) {
        wishlistDao.findByEmailAndProductId(email, productId)
            .ifPresent(v -> {
                throw new IllegalArgumentException(ErrorMessage.WISHLIST_ALREADY_EXISTS_MSG);
            });

        wishlistDao.insertProduct(email, productId);
    }

    /**
     * 위시 리스트에서 상품 삭제
     *
     * @param email
     * @param productId
     */
    public void deleteWishlist(String email, Long productId) {
        wishlistDao.findByEmailAndProductId(email, productId)
            .orElseThrow(() -> new NoSuchElementException(ErrorMessage.WISHLIST_NOT_EXISTS_MSG));
        wishlistDao.deleteProduct(email, productId);
    }
}
