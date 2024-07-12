package gift.domain.service;

import gift.domain.dto.response.WishResponse;
import gift.domain.entity.Wish;
import gift.domain.repository.ProductRepository;
import gift.domain.exception.ProductNotFoundException;
import gift.domain.entity.User;
import gift.domain.dto.response.WishAddResponse;
import gift.domain.dto.request.WishDeleteRequest;
import gift.domain.dto.request.WishRequest;
import gift.domain.exception.ProductNotIncludedInWishlistException;
import gift.domain.repository.WishRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class WishService {

    private final WishRepository wishRepository;
    private final ProductRepository productRepository;

    private void checkProductExists(Long productId) {
        //존재하지 않는 상품이면 예외 발생
        productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
    }

    public WishService(WishRepository wishRepository, ProductRepository productRepository) {
        this.wishRepository = wishRepository;
        this.productRepository = productRepository;
    }

    public List<WishResponse> getWishlist(User user) {
        return wishRepository.findWishlistByUser(user);
    }

    public WishAddResponse addWishlist(User user, WishRequest wishRequest) {
        checkProductExists(wishRequest.productId());
        Optional<Wish> search = wishRepository.findByUserEmailAndProductId(user.id(), wishRequest.productId());

        //아이템이 없고 수량이 1 이상일 때 새 데이터 삽입
        if (search.isEmpty()) {
            if (wishRequest.quantity() <= 0) {
                // 0 이하인 경우 아무 작업 하지 않음
                return new WishAddResponse("nope", 0L);
            }
            wishRepository.save(WishRequest.toEntity(wishRequest, user));
            return new WishAddResponse("create", wishRequest.quantity());
        }

        //수량은 최소한 0 이상이어야 함
        WishRequest newWishRequest = new WishRequest(wishRequest.productId(),
            wishRequest.quantity() + search.get().quantity());

        //업데이트 후 수량이 음수면 delete 수행
        if (newWishRequest.quantity() <= 0) {
            wishRepository.delete(WishRequest.toEntity(newWishRequest, user));
            return new WishAddResponse("delete", 0L);
        }

        // 아이템이 이미 존재하므로 업데이트 수행
        wishRepository.update(WishRequest.toEntity(newWishRequest, user));
        return new WishAddResponse("add", newWishRequest.quantity());
    }

    public WishResponse updateWishlist(User user, WishRequest wishRequest) {
        checkProductExists(wishRequest.productId());
        wishRepository.findByUserEmailAndProductId(user.id(), wishRequest.productId())
            .orElseThrow(ProductNotIncludedInWishlistException::new);
        wishRepository.update(WishRequest.toEntity(wishRequest, user));
        return WishResponse.of(
            wishRequest.quantity(), productRepository.findById(wishRequest.productId()).get());
    }

    public void deleteWishlist(User user, WishDeleteRequest deleteRequestDto) {
        checkProductExists(deleteRequestDto.productId());
        wishRepository.delete(WishDeleteRequest.toEntity(deleteRequestDto, user));
    }
}
