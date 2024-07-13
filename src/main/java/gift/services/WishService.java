package gift.services;


import gift.classes.Exceptions.ProductException;
import gift.domain.Member;
import gift.domain.Product;
import gift.domain.Wish;
import gift.dto.MemberDto;
import gift.dto.WishDto;
import gift.repositories.MemberRepository;
import gift.repositories.ProductRepository;
import gift.repositories.WishRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishService {
    private final WishRepository wishRepository;
    private ProductRepository productRepository;
    private MemberRepository memberRepository;

    @Autowired
    public WishService(WishRepository wishRepository, ProductRepository productRepository,
        MemberRepository memberRepository) {
        this.wishRepository = wishRepository;
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
    }

    //    Wishlist 조회
    @Transactional
    public List<WishDto> getWishListById(Long memberId) {
        List<Wish> wishList = wishRepository.findAllByMemberId(memberId);
        return wishList.stream()
            .map(wish -> new WishDto(
                wish.getMember().getId(),
                wish.getProduct().getId()
            ))
            .collect(Collectors.toList());
    }

//    Wish 추가
    @Transactional
    public void addWish(MemberDto memberDto, Long productId){
        Member member = memberRepository.findByEmail(memberDto.getEmail());
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductException("Product with ID " + productId + " not found"));;

        Wish wish = new Wish();
        wish.setMember(member);
        wish.setProduct(product);
        wishRepository.save(wish);

    }

//    Wish 삭제
    @Transactional
    public void deleteWish(Long memberId, Long productId) {
        wishRepository.deleteByMemberIdAndProductId(memberId, productId);
    }

}
