package gift.service;

import gift.model.Product;
import gift.model.Wish;
import gift.model.dto.LoginMemberDto;
import gift.model.dto.WishRequestDto;
import gift.model.dto.WishResponseDto;
import gift.repository.ProductRepository;
import gift.repository.WishRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishService {

    private static final int PAGE_SIZE = 10;

    private final WishRepository wishRepository;
    private final ProductRepository productRepository;

    public WishService(WishRepository wishRepository, ProductRepository productRepository) {
        this.wishRepository = wishRepository;
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<WishResponseDto> getWishList(LoginMemberDto loginMemberDto, int pageNo,
        String criteria) {
        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE, Sort.by(Direction.ASC, criteria));
        Page<WishResponseDto> page = wishRepository.findAllByMemberId(loginMemberDto.getId(),
            pageable).map(WishResponseDto::from);
        return page.getContent();
    }

    @Transactional
    public void addProductToWishList(WishRequestDto wishRequestDto, LoginMemberDto loginMemberDto) {
        Product product = productRepository.findById(wishRequestDto.getProductId())
            .orElseThrow(() -> new IllegalArgumentException("Wish 값이 잘못되었습니다."));
        wishRepository.save(
            new Wish(loginMemberDto.toEntity(), product, wishRequestDto.getCount()));
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
        wish.changeCount(wishRequestDto.getCount());
    }

    @Transactional
    public void deleteProductInWishList(WishRequestDto wishRequestDto,
        LoginMemberDto loginMemberDto) {
        wishRepository.deleteByMemberIdAndProductId(loginMemberDto.getId(),
            wishRequestDto.getProductId());
    }
}
