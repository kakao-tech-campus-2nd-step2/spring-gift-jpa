package gift.service;

import gift.dto.WishlistResponseDto;
import gift.entity.Member;
import gift.entity.Product;
import gift.entity.Wish;
import gift.repository.MemberRepository;
import gift.repository.ProductRepository;
import gift.repository.WishRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class WishService {
    private final WishRepository wishRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;


    public WishService(WishRepository wishRepository, MemberRepository memberRepository, ProductRepository productRepository) {
        this.wishRepository = wishRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;

    }

    public List<WishlistResponseDto> getWishListByMemberId(Long memberId) {
        List<Wish> wishList = wishRepository.findByMemberId(memberId);
        return wishList.stream()
            .map(wish -> new WishlistResponseDto(wish.getMember().getId(), wish.getProduct().getId()))
            .collect(Collectors.toList());
    }

    public List<Wish> getWishlist(Long memberId) {
        return wishRepository.findByMemberId(memberId);
    }

    public boolean addWishlist(Long memberId, Long productId){
        Optional<Member> member = memberRepository.findById(memberId);
        Optional<Product> product = productRepository.findById(productId);

        if(member.isEmpty() || product.isEmpty()){
            return false;
        }
        Wish wish = new Wish(member.get(), product.get());
        wishRepository.save(wish);
        return true;
    }

    public boolean deleteWishlist(Long memberId, Long productId){
        Optional<Wish> wish = wishRepository.findByMemberIdAndProductId(memberId, productId);
        if (wish.isPresent()){
            wishRepository.delete(wish.get());
            return true;
        }
        return false;
    }

    public Page<Wish> findByMemberId(Long memberId, Pageable pageable) {
        return wishRepository.findByMemberId(memberId, pageable);
    }
}
