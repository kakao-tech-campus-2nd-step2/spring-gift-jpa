package gift.domain.service;

import gift.domain.dto.request.WishDeleteRequest;
import gift.domain.dto.request.WishRequest;
import gift.domain.dto.response.WishAddResponse;
import gift.domain.dto.response.WishResponse;
import gift.domain.entity.Member;
import gift.domain.entity.Product;
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

    public WishService(WishRepository wishRepository, ProductRepository productRepository) {
        this.wishRepository = wishRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    protected Product getProductByIdOrThrow(Long productId) {
        //존재하지 않는 상품이면 예외 발생
        return productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<WishResponse> getWishlist(Member member) {
        return wishRepository.findWishesByMember(member).stream()
            .map(wish -> WishResponse.of(wish.getQuantity(), wish.getProduct()))
            .toList();
    }

    @Transactional
    public WishAddResponse addWishlist(Member member, WishRequest wishRequest) {
        Product product = getProductByIdOrThrow(wishRequest.productId());
        Optional<Wish> search = wishRepository.findWishByMemberAndProduct(member, product);

        //아이템이 없고 수량이 1 이상일 때 새 데이터 삽입
        if (search.isEmpty()) {
            if (wishRequest.quantity() <= 0) {
                // 0 이하인 경우 아무 작업 하지 않음
                return new WishAddResponse("nope", 0L);
            }
            wishRepository.save(new Wish(product, member, wishRequest.quantity()));
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

        return new WishAddResponse("add", wish.getQuantity());
    }

    @Transactional
    public WishResponse updateWishlist(Member member, WishRequest wishRequest) {
        Product product = getProductByIdOrThrow(wishRequest.productId());
        Wish wish = wishRepository.findWishByMemberAndProduct(member, product)
            .orElseThrow(ProductNotIncludedInWishlistException::new);
        wish.set(wishRequest);
        return WishResponse.of(wish.getQuantity(), product);
    }

    @Transactional
    public void deleteWishlist(Member member, WishDeleteRequest deleteRequestDto) {
        Product product = getProductByIdOrThrow(deleteRequestDto.productId());
        wishRepository.deleteByMemberAndProduct(member, product);
    }
}
