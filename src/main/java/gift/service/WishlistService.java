package gift.service;

import gift.repository.MemberRepository;
import gift.repository.ProductRepository;
import gift.entity.Wishlist;
import gift.repository.WishlistRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;


@Service
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public WishlistService(WishlistRepository wishlistRepository, MemberRepository memberRepository, ProductRepository productRepository) {
        this.wishlistRepository = wishlistRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }

    public List<Wishlist> getAllWishlist(String token) {
        var member_id = memberRepository.searchIdByToken(token);
        try {
            return wishlistRepository.findByMember_id(member_id);
        } catch(Exception e) {
            return null;
        }
    }
  
    public void deleteItem(String token, int product_id) {
        var member_id = memberRepository.searchIdByToken(token);
        if(isItem(member_id, product_id)) {
            wishlistRepository.deleteByMember_idAndMember_id(member_id, product_id);
        }
        else {
            throw new NoSuchElementException();
        }
    }

    public void changeNum(String token, int product_id, int num) {
        var member_id = memberRepository.searchIdByToken(token);
        var member = memberRepository.findById(member_id);
        var product = productRepository.findById(product_id);

        try {
            if (num == 0) {
                wishlistRepository.deleteByMember_idAndMember_id(member_id, product_id);
            } else {
                var wishlist = new Wishlist(member, product, num);
                wishlistRepository.save(wishlist);
            }
        }
        catch(Exception e) {
            throw new NoSuchElementException();
        }
    }

    public void addItem(String token, int product_id) {
        var member_id = memberRepository.searchIdByToken(token);
        var member = memberRepository.findById(member_id);
        var product = productRepository.findById(product_id);

        try {
            if (isItem(member_id, product_id)) {
                var num = wishlistRepository.searchNumOfProductByMember_idAndProduct_id(member_id, product_id);
                var wishlist = new Wishlist(member, product, num+1);
                wishlistRepository.save(wishlist);
            } else {
                var wishlist = new Wishlist(member, product, 1);
                wishlistRepository.save(wishlist);
            }
        }
        catch(Exception e) {
            throw new IllegalArgumentException();
        }
    }

    public boolean isItem(int member_id, int product_id) {
        return wishlistRepository.searchNumOfProductByMember_idAndProduct_id(member_id, product_id) > 0;
    }
}
