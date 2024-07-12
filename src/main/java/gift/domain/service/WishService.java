package gift.domain.service;

import gift.domain.dto.request.WishDeleteRequest;
import gift.domain.dto.request.WishRequest;
import gift.domain.dto.response.WishAddResponse;
import gift.domain.dto.response.WishResponse;
import gift.domain.entity.Product;
import gift.domain.entity.User;
import gift.domain.entity.Wish;
import gift.domain.exception.ProductNotFoundException;
import gift.domain.exception.ProductNotIncludedInWishlistException;
import gift.domain.repository.ProductRepository;
import gift.domain.repository.WishRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishService {

    private final WishRepository wishRepository;
    private final ProductRepository productRepository;

    @Transactional
    protected Product getProductByIdOrThrow(Long productId) {
        //존재하지 않는 상품이면 예외 발생
        return productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
    }

    public WishService(WishRepository wishRepository, ProductRepository productRepository) {
        this.wishRepository = wishRepository;
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<WishResponse> getWishlist(User user) {
        return wishRepository.findWishesByUserId(user.getId()).stream()
            .map(wish -> WishResponse.of(wish.getQuantity(), getProductByIdOrThrow(wish.getProductId())))
            .toList();
    }

    @Transactional
    public WishAddResponse addWishlist(User user, WishRequest wishRequest) {
        getProductByIdOrThrow(wishRequest.productId());
        Optional<Wish> search = wishRepository.findWishByUserIdAndProductId(user.getId(), wishRequest.productId());

        //아이템이 없고 수량이 1 이상일 때 새 데이터 삽입
        if (search.isEmpty()) {
            if (wishRequest.quantity() <= 0) {
                // 0 이하인 경우 아무 작업 하지 않음
                return new WishAddResponse("nope", 0L);
            }
            wishRepository.save(wishRequest.toEntity(user));
            return new WishAddResponse("create", wishRequest.quantity());
        }

        Wish wish = search.get();

        //수량은 최소한 0 이상이어야 함
        wish.setQuantity(wishRequest.quantity() + wish.getQuantity());

        //업데이트 후 수량이 음수면 delete 수행
        if (wish.getQuantity() <= 0) {
            wishRepository.delete(wish);
            return new WishAddResponse("delete", 0L);
        }

        // 아이템이 이미 존재하므로 업데이트 수행
        wishRepository.save(wish);
        return new WishAddResponse("add", wish.getQuantity());
    }

    @Transactional
    public WishResponse updateWishlist(User user, WishRequest wishRequest) {
        Product product = getProductByIdOrThrow(wishRequest.productId());
        Wish wish = wishRepository.findWishByUserIdAndProductId(user.getId(), wishRequest.productId())
            .orElseThrow(ProductNotIncludedInWishlistException::new);
        wish.set(wishRequest);
        wishRepository.save(wish);
        return WishResponse.of(wishRequest.quantity(), product);
    }

    @Transactional
    public void deleteWishlist(User user, WishDeleteRequest deleteRequestDto) {
        getProductByIdOrThrow(deleteRequestDto.productId());
        wishRepository.deleteByProductIdAndUserId(deleteRequestDto.productId(), user.getId());
    }
}
