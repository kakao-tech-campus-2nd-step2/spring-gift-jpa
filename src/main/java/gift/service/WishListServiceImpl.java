package gift.service;



import gift.database.JpaMemberRepository;
import gift.database.JpaProductRepository;
import gift.database.JpaWishRepository;
import gift.dto.WishListDTO;
import gift.exceptionAdvisor.MemberNoSuchException;
import gift.model.Member;
import gift.model.Product;
import gift.model.Wish;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class WishListServiceImpl implements WishListService {

    private final JpaMemberRepository jpaMemberRepository;
    private final JpaWishRepository jpaWishRepository;
    private final JpaProductRepository jpaProductRepository;

    public WishListServiceImpl(JpaMemberRepository jpaMemberRepository,
        JpaWishRepository jpaWishRepository, JpaProductRepository jpaProductRepository) {
        this.jpaMemberRepository = jpaMemberRepository;
        this.jpaWishRepository = jpaWishRepository;
        this.jpaProductRepository = jpaProductRepository;
    }

    @Override
    public void addProduct(long memberId, long productId) {
        Member member = jpaMemberRepository.findById(memberId).orElseThrow(MemberNoSuchException::new);
        Product product = jpaProductRepository.findById(productId).orElseThrow();
        Wish wish = new Wish(member, product);
        member.addWish(wish);
        jpaWishRepository.save(wish);
    }

    @Override
    public void deleteProduct(long memberId, long productId) {
        Member member = jpaMemberRepository.findById(memberId).orElseThrow(MemberNoSuchException::new);
        Wish wish = jpaWishRepository.findByMemberIdAndProductId(memberId,productId).orElseThrow();
        member.delWish(wish);
        jpaWishRepository.delete(wish);
    }

    @Override
    public void updateProduct(long memberId, long productId, int productValue) {
        Wish wish = jpaWishRepository.findByMemberIdAndProductId(memberId,productId).orElseThrow();

        wish.setValue(productValue);

    }

    @Override
    public List<WishListDTO> getWishList(long memberId) {
       Member member = jpaMemberRepository.findById(memberId).orElseThrow();
       member.getWishList().stream().map(wish-> new WishListDTO(wish.getMember().getId(),new HashMap<>())
    }

    private Map<String,Integer> toMap(List<Wish> wishList) {

    }
}
