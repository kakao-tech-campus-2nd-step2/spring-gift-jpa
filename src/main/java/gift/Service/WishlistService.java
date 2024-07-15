package gift.Service;

import gift.Model.Member;
import gift.Model.Product;
import gift.Model.Wishlist;
import gift.Repository.MemberRepository;
import gift.Repository.ProductRepository;
import gift.Repository.WishlistRepository;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    public WishlistService(WishlistRepository wishlistRepository, ProductRepository productRepository, MemberRepository memberRepository){
        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
    }

    public Page<Product> getAllWishlist(String email, Pageable pageable) {
        return wishlistRepository.getAllWishlist(email, pageable);

    }

    public Product getProductById(long id){
        return productRepository.findProductById(id);
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
    public void addWishlist(Long memberId, Long productId){
        wishlistRepository.addProductInWishlist(memberId, productId);
    }

    public Long getWishlistId(String email, long id){
        return wishlistRepository.getWishlistIdByMemberEmailAndProductId(email, id);
    }

    public void deleteWishlist(String email, Long productId, Long wishlistId){
        wishlistRepository.changeProductMemberNull(email,productId);
        wishlistRepository.deleteByWishlistId(wishlistId);
    }

    public void checkUserByMemberEmail(String email){
        try {
            memberRepository.findByEmail(email);
        }catch (EmptyResultDataAccessException e){
            throw new IllegalArgumentException("이메일 다름");
        }
    }

    public Member getMemberByEmail(String email){
        return memberRepository.findByEmail(email);
    }
}
