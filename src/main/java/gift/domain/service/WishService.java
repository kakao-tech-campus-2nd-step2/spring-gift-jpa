package gift.domain.service;

import gift.domain.dto.WishResponseDto;
import gift.domain.entity.Wish;
import gift.domain.repository.ProductRepository;
import gift.domain.exception.ProductNotFoundException;
import gift.domain.entity.User;
import gift.domain.dto.WishAddResponseDto;
import gift.domain.dto.WishDeleteRequestDto;
import gift.domain.dto.WishRequestDto;
import gift.domain.exception.ProductNotIncludedInWishlistException;
import gift.domain.repository.WishRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishService {

    private final WishRepository wishRepository;
    private final ProductRepository productRepository;

    private void checkProductExists(Long productId) {
        //존재하지 않는 상품이면 예외 발생
        productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
    }

    @Autowired
    public WishService(WishRepository wishRepository, ProductRepository productRepository) {
        this.wishRepository = wishRepository;
        this.productRepository = productRepository;
    }

    public List<WishResponseDto> getWishlist(User user) {
        return wishRepository.findWishlistByUser(user);
    }

    public WishAddResponseDto addWishlist(User user, WishRequestDto wishRequestDto) {
        checkProductExists(wishRequestDto.productId());
        Optional<Wish> search = wishRepository.findByUserEmailAndProductId(user.id(), wishRequestDto.productId());

        //아이템이 없고 수량이 1 이상일 때 새 데이터 삽입
        if (search.isEmpty()) {
            if (wishRequestDto.quantity() <= 0) {
                // 0 이하인 경우 아무 작업 하지 않음
                return new WishAddResponseDto("nope", 0L);
            }
            wishRepository.save(WishRequestDto.toEntity(wishRequestDto, user));
            return new WishAddResponseDto("create", wishRequestDto.quantity());
        }

        //수량은 최소한 0 이상이어야 함
        WishRequestDto newWishRequestDto = new WishRequestDto(wishRequestDto.productId(),
            wishRequestDto.quantity() + search.get().quantity());

        //업데이트 후 수량이 음수면 delete 수행
        if (newWishRequestDto.quantity() <= 0) {
            wishRepository.delete(WishRequestDto.toEntity(newWishRequestDto, user));
            return new WishAddResponseDto("delete", 0L);
        }

        // 아이템이 이미 존재하므로 업데이트 수행
        wishRepository.update(WishRequestDto.toEntity(newWishRequestDto, user));
        return new WishAddResponseDto("add", newWishRequestDto.quantity());
    }

    public WishResponseDto updateWishlist(User user, WishRequestDto wishRequestDto) {
        checkProductExists(wishRequestDto.productId());
        wishRepository.findByUserEmailAndProductId(user.id(), wishRequestDto.productId())
            .orElseThrow(ProductNotIncludedInWishlistException::new);
        wishRepository.update(WishRequestDto.toEntity(wishRequestDto, user));
        return WishResponseDto.of(
            wishRequestDto.quantity(), productRepository.findById(wishRequestDto.productId()).get());
    }

    public void deleteWishlist(User user, WishDeleteRequestDto deleteRequestDto) {
        checkProductExists(deleteRequestDto.productId());
        wishRepository.delete(WishDeleteRequestDto.toEntity(deleteRequestDto, user));
    }
}
