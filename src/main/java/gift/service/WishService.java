package gift.service;

import gift.domain.model.dto.WishResponseDto;
import gift.domain.model.dto.WishUpdateRequestDto;
import gift.domain.model.entity.Product;
import gift.domain.model.entity.User;
import gift.domain.model.entity.Wish;
import gift.domain.model.enums.WishSortBy;
import gift.domain.repository.WishRepository;
import gift.exception.DuplicateWishItemException;
import gift.exception.NoSuchWishException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishService {

    private final WishRepository wishRepository;
    private final ProductService productService;
    private final UserService userService;
    private static final int PAGE_SIZE = 10;

    public WishService(WishRepository wishRepository, ProductService productService,
        UserService userService) {
        this.wishRepository = wishRepository;
        this.productService = productService;
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    public Page<WishResponseDto> getWishes(String email, int page, WishSortBy sortBy) {
        Sort sort = sortBy.getSort();
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, sort);

        Page<Wish> wishPage = wishRepository.findByUserEmail(email, pageable);

        return wishPage.map(this::convertToWishResponseDto);
    }

    @Transactional
    public WishResponseDto addWish(String email, Long productId) {
        productService.validateExistProductId(productId);
        if (wishRepository.existsByUserEmailAndProductId(email, productId)) {
            throw new DuplicateWishItemException("이미 위시리스트에 존재하는 상품입니다.");
        }
        User user = userService.getUserByEmail(email);
        Product product = productService.convertResponseDtoToEntity(
            productService.getProduct(productId));

        Wish wish = new Wish(user, product);
        return convertToWishResponseDto(wishRepository.save(wish));
    }

    @Transactional
    public void deleteWish(String email, Long productId) {
        productService.validateExistProductId(productId);
        validateExistWishProduct(email, productId);
        wishRepository.deleteByUserEmailAndProductId(email, productId);
    }

    @Transactional
    public WishResponseDto updateWish(String email,
        WishUpdateRequestDto wishUpdateRequestDto) {
        productService.validateExistProductId(wishUpdateRequestDto.getProductId());
        Wish wish = validateExistWishProduct(email, wishUpdateRequestDto.getProductId());
        wish.setCount(wishUpdateRequestDto.getCount());
        return convertToWishResponseDto(wishRepository.save(wish));
    }

    @Transactional(readOnly = true)
    public Wish validateExistWishProduct(String email, Long productId) {
        return wishRepository.findByUserEmailAndProductId(email, productId)
            .orElseThrow(() -> new NoSuchWishException("위시리스트에 존재하지 않는 상품입니다."));
    }

    private WishResponseDto convertToWishResponseDto(Wish wish) {
        return new WishResponseDto(
            wish.getId(),
            wish.getCount(),
            wish.getProduct().getId(),
            wish.getProduct().getName(),
            wish.getProduct().getPrice(),
            wish.getProduct().getImageUrl()
        );
    }
}