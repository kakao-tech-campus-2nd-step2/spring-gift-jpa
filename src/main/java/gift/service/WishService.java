package gift.service;

import gift.model.Product;
import gift.model.Wish;
import gift.model.dto.LoginMemberDto;
import gift.model.dto.WishRequestDto;
import gift.model.dto.WishResponseDto;
import gift.repository.ProductRepository;
import gift.repository.WishRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishService {

    private final WishRepository wishRepository;
    private final ProductRepository productRepository;

    public WishService(WishRepository wishRepository, ProductRepository productRepository) {
        this.wishRepository = wishRepository;
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<WishResponseDto> getWishList(LoginMemberDto loginMemberDto) {
        return wishRepository.findAllByMemberId(loginMemberDto.getId())
            .stream()
            .map(WishResponseDto::from)
            .toList();
    }

    @Transactional
    public void addProductToWishList(WishRequestDto wishRequestDto, LoginMemberDto loginMemberDto) {
        Product product = productRepository.findById(wishRequestDto.getProductId())
            .orElseThrow(() -> new IllegalArgumentException("Wish 값이 잘못되었습니다."));
        wishRepository.save(new Wish(loginMemberDto.toEntity(), product, wishRequestDto.getCount()));
    }

    @Transactional
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
    }

    @Transactional
    public void deleteProductInWishList(WishRequestDto wishRequestDto,
        LoginMemberDto loginMemberDto) {
        wishRepository.deleteByMemberIdAndProductId(loginMemberDto.getId(),
            wishRequestDto.getProductId());
    }
}
