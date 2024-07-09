package gift.service;

<<<<<<< HEAD
<<<<<<< HEAD
import gift.domain.model.*;
import gift.domain.repository.WishRepository;
=======
import gift.domain.model.ProductResponseDto;
import gift.domain.model.WishResponseDto;
import gift.domain.model.WishUpdateRequestDto;
import gift.domain.repository.WishListRepository;
>>>>>>> 4b5ff17 (refacotr: ProductRepository JPA 방식으로 리팩토링)
=======
import gift.domain.model.*;
import gift.domain.repository.WishRepository;
>>>>>>> 96f4edd (refacotr: WishRepository JPA 방식으로 리팩토링)
import gift.exception.DuplicateWishItemException;
import gift.exception.NoSuchWishException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;

@Service
public class WishListService {

    private final WishRepository wishRepository;
    private final ProductService productService;
    private final UserService userService;

    public WishListService(WishRepository wishRepository, ProductService productService,
        UserService userService) {
        this.wishRepository = wishRepository;
        this.productService = productService;
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    public List<WishResponseDto> getProductsByUserEmail(String email) {
        return wishRepository.findByUserEmail(email).stream()
            .map(this::convertToWishResponseDto)
            .collect(Collectors.toList());
    }

<<<<<<< HEAD
<<<<<<< HEAD
    @Transactional
    public WishResponseDto addWish(String email, Long productId) {
=======
    public ProductResponseDto addWish(String email, Long productId) {
>>>>>>> 4b5ff17 (refacotr: ProductRepository JPA 방식으로 리팩토링)
=======
    @Transactional
    public WishResponseDto addWish(String email, Long productId) {
>>>>>>> 96f4edd (refacotr: WishRepository JPA 방식으로 리팩토링)
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
    public void deleteWishProduct(String email, Long productId) {
        productService.validateExistProductId(productId);
        validateExistWishProduct(email, productId);
        wishRepository.deleteByUserEmailAndProductId(email, productId);
    }

    @Transactional
    public WishResponseDto updateWishProduct(String email, WishUpdateRequestDto wishUpdateRequestDto) {
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