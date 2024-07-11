// WishService.java
package gift.service;

import gift.dto.WishResponse;
import gift.model.Member;
import gift.model.Product;
import gift.model.Wish;
import gift.repository.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class WishService {

    private final WishRepository wishRepository;

    @Autowired
    public WishService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

    public Page<WishResponse> getWishesByMemberId(Long memberId, Pageable pageable) {
        Page<Wish> wishes = wishRepository.findByMemberId(memberId, pageable);
        return wishes.map(this::convertToWishResponse);

    }

    public Wish addWish(Member member, Product product) {
        Wish wish = Wish.builder()
                .member(member)
                .product(product)
                .build();
        return wishRepository.save(wish);
    }

    public void deleteWish(Long wishId) {
        if (!wishRepository.existsById(wishId)) {
            throw new IllegalArgumentException("Wish not found with id: " + wishId);
        }
        wishRepository.deleteById(wishId);
    }

    private WishResponse convertToWishResponse(Wish wish) {
        return WishResponse.builder()
                .id(wish.getId())
                .productName(wish.getProduct().getName())
                .productPrice(wish.getProduct().getPrice())
                .productImageurl(wish.getProduct().getImageurl())
                .build();
    }
}