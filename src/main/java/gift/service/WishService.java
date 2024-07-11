package gift.service;

import gift.model.Wish;
import gift.model.dto.LoginMemberDto;
import gift.model.dto.WishRequestDto;
import gift.model.dto.WishResponseDto;
import gift.repository.MemberRepository;
import gift.repository.ProductRepository;
import gift.repository.WishRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WishService {

    private final WishRepository wishRepository;
    private final ProductRepository productRepository;

    public WishService(WishRepository wishRepository, ProductRepository productRepository) {
        this.wishRepository = wishRepository;
        this.productRepository = productRepository;
    }

    public List<WishResponseDto> getWishList(LoginMemberDto loginMemberDto) {
        return wishRepository.findAllByMemberId(loginMemberDto.getId())
            .stream()
            .map(WishResponseDto::from)
            .toList();
    }

    public void addProductToWishList(WishRequestDto wishRequestDto, LoginMemberDto loginMemberDto) {
        Wish wish = wishRequestDto.toEntity();
        wish.setProduct(productRepository.findById(wishRequestDto.getProductId()).get());
        wish.setMember(loginMemberDto.toEntity());
        wishRepository.save(wish);
    }

    public void updateProductInWishList(WishRequestDto wishRequestDto,
        LoginMemberDto loginMemberDto) {
        if (wishRequestDto.isCountZero()) {
            wishRepository.deleteByMemberIdAndProductId(loginMemberDto.getId(),
                wishRequestDto.getProductId());
            return;
        }
        Wish wish = wishRepository.findByMemberIdAndProductId(loginMemberDto.getId(),
            wishRequestDto.getProductId());
        wish.setCount(wishRequestDto.getCount());
        wishRepository.save(wish);
    }

    public void deleteProductInWishList(WishRequestDto wishRequestDto,
        LoginMemberDto loginMemberDto) {
        wishRepository.deleteByMemberIdAndProductId(loginMemberDto.getId(),
            wishRequestDto.getProductId());
    }
}
