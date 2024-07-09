package gift.Service;

import gift.Model.Member;
import gift.Model.Product;
import gift.Repository.MemberRepository;
import gift.Repository.ProductRepository;
import gift.Repository.WishlistRepository;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
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

    public List<Product> getAllWishlist() {
        return wishlistRepository.findAllWishlist();
    }
    public Product getProductById(long id){
        return productRepository.findProductById(id);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    public void addWishlist(Product product){
        wishlistRepository.addWishlistFromProduct(product);
    }

    public void deleteWishlist(Long id){
        wishlistRepository.deleteWishlistById(id);
    }

    public void checkUserByMemberEmail(String email){
        try {
            memberRepository.findByEmail(email);
        }catch (EmptyResultDataAccessException e){
            throw new IllegalArgumentException("이메일 다름");
        }
    }
}
